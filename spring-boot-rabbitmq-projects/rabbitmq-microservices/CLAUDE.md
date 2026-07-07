# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Three independent Spring Boot 3 (Java 17) apps demonstrating a RabbitMQ pub/sub microservices flow. There is no parent/aggregator pom — each app has its own `pom.xml` and Maven wrapper and is built/run from its own directory.

- `user-service` (port 8081) — producer. REST endpoints publish messages to a topic exchange.
- `sms-service` (port 8082) — consumer. Listens on the SMS queue.
- `email-service` (port 8083) — consumer. Listens on the email queue.

All three run against the same local RabbitMQ broker (default `localhost:5672`, guest/guest).

## Build, run, test commands

Run from inside each service's directory using its Maven wrapper (`mvnw.cmd` on native Windows shells):

```bash
./mvnw clean install          # build
./mvnw spring-boot:run        # run
./mvnw test                   # run all tests
./mvnw test -Dtest=ClassName  # run a single test class
```

To exercise the full flow, start RabbitMQ, then start all three services (each in its own terminal) before hitting `user-service`'s endpoints.

## RabbitMQ topology

`user-service` (`config/UserRabbitMQConfig.java`) declares the exchange, both queues, and both bindings — it owns the topology even though it's only a producer. `sms-service` and `email-service` only declare a `RabbitTemplate`/`Jackson2JsonMessageConverter` bean and consume via `@RabbitListener(queues = "${...queue.name}")`; they don't redeclare the exchange or bindings, so they rely on `user-service` (or a prior run of it) having created them, and their queue name property values must match `user-service`'s literal queue names.

- Exchange: `user_microservice_exchange` (topic), defined only in `user-service`'s `application.properties`.
- SMS queue: `user_sms_queue`, routing key `user_sms_service_key`.
- Email queue: `user_email_queue`, routing key `user_email_service_key`.

Messages are serialized as JSON (`Jackson2JsonMessageConverter`) and deserialized straight into each service's own local `UserDto` (each service has its own copy under `dto/UserDto.java` — there's no shared library, so keep their fields in sync manually when changing the message shape).

## Request flow

`POST /api/v1/email` and `POST /api/v1/sms` on `user-service` (`UserController` → `UserService`) both take a `UserDto` body, publish it to the exchange with the corresponding routing key via `RabbitTemplate.convertAndSend`, and echo the same `UserDto` back in the response — the HTTP response does not wait on or reflect consumer processing. `sms-service`'s `SMSService.getSMS` and `email-service`'s `EmailService.receiveEmail` just log the received payload; there's no further processing to model against.