# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A single-module Spring Boot 3 / Java 17 demo app (`com.catmanscodes.rabbitmq`) showing basic RabbitMQ producer/consumer usage via `spring-boot-starter-amqp`. There is no database — this app only exercises messaging.

## Build, run, test

Run from this directory using the Maven wrapper (`mvnw.cmd` on native Windows shells):

```bash
./mvnw clean install
./mvnw spring-boot:run
./mvnw test
./mvnw test -Dtest=ClassName
./mvnw test -Dtest=ClassName#methodName
```

RabbitMQ itself is not embedded — start it first via the included `docker-compose.yml`:

```bash
docker compose up -d
```

This brings up `rabbitmq:3-management` with the management UI on `http://localhost:15672` (guest/guest) and AMQP on `5672`. The app connects to `localhost:5672` with `guest`/`guest` (see `application.properties`). App runs on port `8080`.

## Architecture

Standard `controller -> service -> (RabbitTemplate / @RabbitListener)` layering, no repository layer since there's no persistence:

- `config/RabbitMqConfig.java` — declares the single `TopicExchange` (`springboot_app_exchange`) and two queues bound to it with distinct routing keys: a plain-string queue (`springboot_app_queue`) and an `Employee` object queue (`springboot_app_employee_queue`). Also registers `Jackson2JsonMessageConverter` on the `RabbitTemplate` so non-string payloads (e.g. `Employee`) are serialized as JSON on the wire.
- `controller/ProducerController.java` — exposes `GET /api/v1/publish?message=...` (sends a raw string) and `POST /api/v1/publish` (sends an `Employee` body, server-assigns a random UUID `id`) — both overloads share the same path, distinguished by HTTP method.
- `service/RabbitMQProducerService.java` — wraps `RabbitTemplate.convertAndSend(exchange, routingKey, payload)` for both message types.
- `service/RabbitMQConsumerService.java` — `@RabbitListener` methods bound to each queue name (via `${...}` property placeholders), just logs received messages/objects.

All queue/exchange/routing-key names are externalized in `application.properties` and injected via `@Value` — when adding a new message type, follow the existing pattern: add a queue + binding in `RabbitMqConfig`, a routing-key property, a producer method, and a listener method.