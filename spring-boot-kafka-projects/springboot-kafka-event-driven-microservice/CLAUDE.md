# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project status

This is a **scaffold-stage** multi-module Kafka event-driven microservice demo, separate from `spring-boot-kafka-app` and `springboot-kafka-real-world-project` elsewhere in the parent monorepo (see the repo-root `CLAUDE.md` for the full monorepo map). There is no parent/aggregator pom — each service below is its own independent Maven project, generated from Spring Initializr and not yet built out with business logic, controllers, or Kafka producer/consumer code.

## Structure

| Service | Path | Base package |
|---|---|---|
| Order Service | `order-service/` | `com.catmanscodes.order_service` |
| Stock Service | `stock-service/` | `com.catmanscodes.stock_service` |
| Email Service | `email-service/` | `com.catmanscodes.email_service` |

All three currently contain only the generated `*Application.java` entry point and a minimal `application.properties` (just `spring.application.name`). Given the naming, the intended flow is an order-placement event chain (order → stock check/reservation → email notification) over Kafka topics, but that wiring does not exist yet — confirm current state by checking each service's source before assuming any topic/consumer/producer exists.

All three share the same dependency set: `spring-boot-starter-web`, `spring-kafka` (+ `spring-kafka-test` for tests), Lombok, and target Java 17 / Spring Boot 3.5.16.

## Build, run, test commands

Run from inside each service's own directory (no root-level build):

```bash
./mvnw clean install          # build (mvnw.cmd on native Windows shells)
./mvnw spring-boot:run         # run
./mvnw test                    # run all tests
./mvnw test -Dtest=ClassName   # run a single test class
./mvnw test -Dtest=ClassName#methodName   # run a single test method
```

All three services default to `server.port=8080` and none override it yet — only run one at a time until ports are configured for concurrent operation.

## Kafka setup

No `spring.kafka.*` properties are configured in any service yet (bootstrap servers, topics, consumer groups, serializers). Local Kafka setup steps (ZooKeeper + broker) are documented in `spring-boot-kafka-projects/README.md` one level up.