# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository structure

This is a monorepo of independent Spring Boot 3 / Java 17 demo applications. There is **no parent/aggregator pom** at the repo root — each app is built and run separately from its own directory.

| App | Path | Purpose |
|---|---|---|
| Banking Application | `banking-application/` | REST CRUD for accounts/transactions, MySQL |
| Expense Tracker | `expense-tracker-application/` | REST CRUD for expenses/categories, MySQL, OpenAPI docs |
| Student Management System | `student-management-system/` | Spring MVC + Thymeleaf server-rendered CRUD app, MySQL |
| Todo APIs | `todo-apis/` | REST CRUD with JWT-based Spring Security auth |
| User APIs | `user-apis/` | REST CRUD for users, standalone (also consumed conceptually by `todo-apis`) |
| Kafka demo app | `spring-boot-kafka-projects/spring-boot-kafka-app/` | Single-module producer/consumer demo (string + JSON messages) |
| Kafka real-world project | `spring-boot-kafka-projects/springboot-kafka-real-world-project/` | Multi-module Maven build (`kafka-producer-wikimedia`, `kafka-consumer-database`) |
| Kafka event-driven microservices | `spring-boot-kafka-projects/springboot-kafka-event-driven-microservice/` | Three independent services: `order-service` (producer), `stock-service`, `email-service` (consumers) |
| RabbitMQ demo app | `spring-boot-rabbitmq-projects/rabbitmq-demo/` | Single-module demo: topic exchange, string + JSON (`Employee`) messages, Docker Compose |
| RabbitMQ microservices | `spring-boot-rabbitmq-projects/rabbitmq-microservices/` | Three independent services: `user-service` (producer), `sms-service`, `email-service` (consumers) |

Each app under the four main folders (`banking-application`, `expense-tracker-application`, `student-management-system`, plus `todo-apis`, `user-apis`) follows the same internal layering: `controller -> service (+ impl) -> repository (Spring Data JPA)`, with `dto`, `entity`/`model`, `mapper`, and `exception` packages alongside. Look at one app's package layout before adding code to another — conventions are consistent across apps even though base packages differ (`org.catmanscodes.*` vs `com.catmanscodes.*`).

## Build, run, test commands

All commands are run **from inside the specific app's directory** using its Maven wrapper.

```bash
# Build
./mvnw clean install          # (mvnw.cmd on native Windows shells)

# Run
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName
```

For the multi-module Kafka project (`spring-boot-kafka-projects/springboot-kafka-real-world-project`), run Maven from that project's root to build both `kafka-producer-wikimedia` and `kafka-consumer-database` modules together, or `cd` into a module to build it individually.

## Database setup

All non-Kafka apps use MySQL with `spring.jpa.hibernate.ddl-auto=update` (schema is auto-managed from entities — no manual migrations). Each app expects its own pre-created database and uses local default credentials (`root`/`root`) in `application.properties`:

- `banking-application` → `banking_app`
- `expense-tracker-application` → `expense_tracker_app`
- `student-management-system` → `student_management`
- `todo-apis` → `todo_app`
- `user-apis` → `user`

All apps default to `server.port=8080`, so **only run one app at a time** unless ports are overridden.

## Kafka local setup

`spring-boot-kafka-projects/README.md` documents the Windows-specific steps for starting ZooKeeper and the Kafka broker locally (`kafka_2.13-3.7.0`), creating a topic, and producing/consuming via the CLI before running the Spring Boot Kafka apps. Both Kafka apps connect to `localhost:9092` by default.

## Security (todo-apis)

`todo-apis` is the only app with authentication: stateless JWT auth via `security/JwtAuthenticationFilter`, `JwtTokenProvider`, and `JwtAuthenticationEntryPoint`, configured in `config/AppSecurityConfig.java`. JWT secret and expiration are set in `application.properties` (`app.jwt-secret`, `app.jwt-expiration-milliseconds`) — these are demo/local values, not production secrets.

## API documentation

Postman collections for manual API testing live alongside each app (e.g. `banking-application/Postman-Clients/`, `todo-apis/POSTMAN-APIs/`, `user-apis/Postman/`). `expense-tracker-application` additionally exposes Swagger/OpenAPI UI at `http://localhost:8080/swagger-ui/index.html` when running.
