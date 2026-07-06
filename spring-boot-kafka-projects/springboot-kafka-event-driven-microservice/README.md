# Spring Boot Kafka Event-Driven Microservice

A minimal event-driven microservice demo built with Spring Boot 3 and Apache Kafka. An `order-service` publishes order events to Kafka, and `stock-service` / `email-service` consume them independently.

## Architecture

```
                 order_event_topic
  order-service  ───────────────►  stock-service   (consumer group: stock-group)
  (producer)     ───────────────►  email-service   (consumer group: email-group)
```

- **order-service** exposes a REST endpoint that accepts an order, assigns it an ID/timestamp, and publishes an `OrderEvent` to Kafka.
- **stock-service** and **email-service** each consume `OrderEvent` messages from the same topic in their own consumer group, so both receive every event independently.
- Stock/email business logic (stock reservation, sending an email) is not implemented yet — each listener currently just logs the received event.

## Services

| Service | Path | Base package | Port | Role |
|---|---|---|---|---|
| Order Service | `order-service/` | `com.catmanscodes.order_service` | 8081 | Kafka producer + REST API |
| Stock Service | `stock-service/` | `com.catmanscodes.stock_service` | 8082 | Kafka consumer (`stock-group`) |
| Email Service | `email-service/` | `com.catmanscodes.email_service` | 8083 | Kafka consumer (`email-group`) |

Each service is an independent Maven project (no parent/aggregator pom) generated from Spring Initializr, targeting Java 17 / Spring Boot 3.5.16, with `spring-boot-starter-web`, `spring-kafka` (+ `spring-kafka-test`), and Lombok. `order-service` additionally uses `spring-boot-starter-validation` for request validation.

## Prerequisites

- Java 17
- A local Kafka broker (+ ZooKeeper) running on `localhost:9092` — see `../README.md` (one level up) for Windows setup steps, or run Kafka via Docker.
- The topic `order_event_topic` is auto-created by `order-service` on startup (`KafkaConfig` registers a `NewTopic` bean), so no manual topic creation is required.

## Running

Each service is built and run independently from its own directory using the Maven wrapper.

```bash
# Terminal 1 — order-service
cd order-service
./mvnw spring-boot:run      # mvnw.cmd on native Windows shells

# Terminal 2 — stock-service
cd stock-service
./mvnw spring-boot:run

# Terminal 3 — email-service
cd email-service
./mvnw spring-boot:run
```

All three ports (8081/8082/8083) are distinct, so the services can run concurrently.

## Testing the flow

Publish an order via `order-service`'s REST API:

```bash
curl -X POST http://localhost:8081/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderStatus": "CREATED",
    "orderType": "ONLINE",
    "customerId": "cust-001",
    "customerName": "Jane Doe",
    "customerPhone": "555-0100",
    "customerAddress": "123 Main St"
  }'
```

The response is the generated `orderEventId`. Watch the `stock-service` and `email-service` console logs — both should log receipt of the same order event.

## Build & test

```bash
./mvnw clean install          # build (run inside each service directory)
./mvnw test                    # run all tests
./mvnw test -Dtest=ClassName   # run a single test class
```

## Kafka configuration reference

| Property | order-service | stock-service | email-service |
|---|---|---|---|
| `spring.kafka.*.bootstrap-servers` | `localhost:9092` | `localhost:9092` | `localhost:9092` |
| Serializer/Deserializer | `StringSerializer` / `JsonSerializer` | `StringDeserializer` / `JsonDeserializer` | `StringDeserializer` / `JsonDeserializer` |
| `spring.kafka.consumer.group-id` | — | `stock-group` | `email-group` |
| `spring.kafka.topic.name` | `order_event_topic` | `order_event_topic` | `order_event_topic` |
| Producer reliability | `acks=all`, idempotence enabled, `retries=3` | — | — |

## Status / next steps

- [x] `order-service`: REST endpoint + Kafka producer + auto topic creation
- [x] `stock-service` / `email-service`: Kafka consumers logging received events
- [ ] Stock reservation business logic in `stock-service`
- [ ] Actual email-sending logic in `email-service`
- [ ] Error handling / retry topics / dead-letter queue
- [ ] Concurrent multi-service local run docs (Docker Compose for Kafka + all three services)