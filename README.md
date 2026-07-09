# Spring Boot 3 Projects with Line-by-Line Code

A collection of independent Spring Boot 3 / Java 17 demo applications, each in its own folder with its own Maven build (no parent/aggregator POM at the repo root). Run one app at a time — they all default to port `8080`.

---

## 1. Banking Application

**Path:** `banking-application/`

Demo REST API for core banking operations: account management, deposits/withdrawals, and fund transfers between accounts.

- **Tech stack:** Java 17, Spring Boot 3.2.5, Spring Data JPA, MySQL
- **Features:**
  - CRUD operations on accounts
  - Deposit and withdraw funds
  - Transfer funds between accounts (with transaction records)
  - View transaction history per account
  - Global exception handling for invalid operations (e.g. insufficient balance)
- **Base package:** `org.catmanscodes.bankingapp`
- **Database:** MySQL database `banking_app` (`spring.jpa.hibernate.ddl-auto=update`)
- **Endpoints** (`/api/accounts`):
  | Method | Path | Description |
  |---|---|---|
  | POST | `/create` | Create a new account |
  | GET | `/{id}` | Get account by id |
  | GET | `` | List all accounts |
  | PUT | `/{id}/deposit` | Deposit into account |
  | PUT | `/{id}/withdraw` | Withdraw from account |
  | POST | `/transfer` | Transfer funds between two accounts |
  | GET | `/{id}/transactions` | List transactions for an account |
  | DELETE | `/{id}` | Delete account |
- **API testing:** Postman collection at `banking-application/Postman-Clients/BankingApp.postman_collection.json`

---

## 2. Expense Tracker Application

**Path:** `expense-tracker-application/`

REST API for tracking personal expenses grouped by category.

- **Tech stack:** Java 17, Spring Boot 3.2.5, Spring Data JPA, MySQL, OpenAPI 3 (springdoc), Java records for DTOs
- **Features:**
  - CRUD operations for expenses and categories
  - Many-to-one relationship between `Expense` and `Category` (Hibernate)
  - Global and custom exception handling (`ResourceNotFoundException`)
  - OpenAPI/Swagger API documentation
- **Base package:** `org.catmanscodes.eta`
- **Database:** MySQL database `expense_tracker_app` (`spring.jpa.hibernate.ddl-auto=update`)
- **Endpoints:**
  | Method | Path | Description |
  |---|---|---|
  | POST | `/api/categories/create` | Create a category |
  | GET | `/api/categories/{id}` | Get category by id |
  | GET | `/api/categories` | List categories |
  | PUT | `/api/categories/{id}/update` | Update category |
  | DELETE | `/api/categories/{id}` | Delete category |
  | POST | `/api/expenses/create` | Create an expense |
  | GET | `/api/expenses/{id}` | Get expense by id |
  | GET | `/api/expenses` | List expenses |
  | PUT | `/api/expenses/{id}/update` | Update expense |
  | DELETE | `/api/expenses/{id}` | Delete expense |
- **API docs:** http://localhost:8080/swagger-ui/index.html
- **API testing:** Postman collection at `expense-tracker-application/PostMan-Clients/ExpecseTrackingApp.postman_collection.json`

---

## 3. Student Management System

**Path:** `student-management-system/`

Server-rendered (Spring MVC + Thymeleaf) web application for managing student records.

- **Tech stack:** Java 17, Spring Boot 3.2.5, Spring MVC, Spring Data JPA, Thymeleaf, Bootstrap, Hibernate Validator, MySQL
- **Features:** full CRUD UI — view all students, create, edit, view detail, delete
- **Base package:** `org.catmanscodes.sms`
- **Templates:** `student-list.html`, `student-create.html`, `student-edit.html`, `student-detail.html` (under `src/main/resources/templates`)
- **Database:** MySQL database `student_management` (`spring.jpa.hibernate.ddl-auto=update`, MySQL dialect explicitly set)
- **Routes:**
  | Method | Path | Description |
  |---|---|---|
  | GET | `/students` | List all students |
  | GET | `/students/load` | Load create-student form |
  | POST | `/students/save` | Save a new student |
  | GET | `/students/{studentId}/edit` | Load edit form |
  | POST | `/students/update/{studentId}` | Update student |
  | GET | `/students/{studentId}/view` | View student detail |
  | GET | `/students/{studentId}/delete` | Delete student |

---

## 4. Todo APIs

**Path:** `todo-apis/`

REST API for managing todos, secured with JWT-based authentication.

- **Tech stack:** Java 17, Spring Boot 3.5.15, Spring Security, JJWT 0.12.7, Spring Data JPA, MySQL
- **Features:**
  - User registration and login issuing JWT access tokens
  - Stateless JWT auth filter chain (`JwtAuthenticationFilter`, `JwtTokenProvider`, `JwtAuthenticationEntryPoint`, configured in `AppSecurityConfig`)
  - Role-based `User`/`Role` entities
  - CRUD operations on todos, scoped per authenticated user
  - Global exception handling
- **Base package:** `com.catmanscodes.todoapis`
- **Database:** MySQL database `todo_app`, HikariCP pool tuning, `spring.jpa.hibernate.ddl-auto=update`
- **JWT config:** `app.jwt-secret`, `app.jwt-expiration-milliseconds` in `application.properties` (demo values — not for production)
- **Endpoints:**
  | Method | Path | Description |
  |---|---|---|
  | POST | `/api/auth/register` | Register a new user |
  | POST | `/api/auth/login` | Login and receive a JWT |
  | POST | `/api/todos/create` | Create a todo |
  | GET | `/api/todos` | List todos |
  | GET | `/api/todos/{id}` | Get todo by id |
  | PUT | `/api/todos/{id}` | Update todo |
  | DELETE | `/api/todos/{id}` | Delete todo |
- **API testing:** Postman collection at `todo-apis/POSTMAN-APIs/Todo-App.postman_collection.json`

---

## 5. User APIs

**Path:** `user-apis/`

Standalone REST API for managing users (CRUD), independent of `todo-apis`.

- **Tech stack:** Java 17, Spring Boot 3.5.15, Spring Data JPA, MySQL, Logback (custom `logback-spring.xml`)
- **Features:** standard CRUD for users with global/custom exception handling, HikariCP pool tuning
- **Base package:** `com.catmanscodes.userapis`
- **Database:** MySQL database `user` (`spring.jpa.hibernate.ddl-auto=update`)
- **Endpoints** (`/api/v1/users`):
  | Method | Path | Description |
  |---|---|---|
  | GET | `` | List all users |
  | GET | `/{id}` | Get user by id |
  | POST | `` | Create a user |
  | PUT | `/{id}` | Update a user |
  | DELETE | `/{id}` | Delete a user |
- **API testing:** Postman collection at `user-apis/Postman/User CRUD.postman_collection.json`

---

## 6. Spring Boot & Apache Kafka Projects

**Path:** `spring-boot-kafka-projects/`

Two separate Kafka projects exploring producer/consumer patterns with Spring Boot.

### 6.1 Kafka Demo App
**Path:** `spring-boot-kafka-projects/spring-boot-kafka-app/`

- **Tech stack:** Java 17, Spring Boot 3.3.0, Spring Kafka
- **Features:** demonstrates plain string messages and JSON-serialized messages via separate producer/consumer pairs (`KafkaProducer`/`KafkaConsumer` and `JsonKafkaProducer`/`JsonKafkaConsumer`), topic configuration via `KafkaTopicConfig`
- **Base package:** `org.catmanscode.springbootkafka`
- **Endpoints** (`/api/v1/kafka`):
  | Method | Path | Description |
  |---|---|---|
  | POST | `/publish` | Publish a message to Kafka |
  | GET | `/publish` | Publish a JSON message to Kafka |
- **Broker:** `localhost:9092` (configured in `application.properties`)
- **API testing:** Postman collection under `spring-boot-kafka-app/Postman-Clinet/`

### 6.2 Kafka Real-World Project
**Path:** `spring-boot-kafka-projects/springboot-kafka-real-world-project/`

Multi-module Maven project (`packaging=pom`) simulating a real-world event pipeline:

- **`kafka-producer-wikimedia`** — produces Kafka events from the Wikimedia recent-changes event stream
- **`kafka-consumer-database`** — consumes Kafka events and persists them to a database

### 6.3 Kafka Event-Driven Microservices

**Path:** `spring-boot-kafka-projects/springboot-kafka-event-driven-microservice/`

Three independent Spring Boot services demonstrating a Kafka pub/sub event-driven flow: one producer (`order-service`) and two consumers (`stock-service`, `email-service`), each in its own consumer group so both receive every event independently.

- **Tech stack:** Java 17, Spring Boot 3.5.16, Spring Kafka, Lombok, Spring Validation (`order-service` only)
- **Topic:** `order_event_topic` — auto-created by `order-service` on startup via a `NewTopic` bean (no manual creation needed)

| Service | Port | Role | Consumer Group |
|---|---|---|---|
| `order-service` | 8081 | Producer — REST endpoint + Kafka publisher | — |
| `stock-service` | 8082 | Consumer — logs received `OrderEvent` | `stock-group` |
| `email-service` | 8083 | Consumer — logs received `OrderEvent` | `email-group` |

- **Endpoint** (on `order-service`):
  | Method | Path | Description |
  |---|---|---|
  | POST | `/api/v1/orders` | Publish an `OrderEvent` to Kafka |
- **Flow:** `order-service` publishes an `OrderEvent` (JSON) to `order_event_topic`; both `stock-service` and `email-service` consume it independently and log it
- **Broker:** `localhost:9092` (see local Kafka setup below)

### Local Kafka setup (Windows)

Documented in `spring-boot-kafka-projects/README.md`:

1. Download Kafka (`kafka_2.13-3.7.0`) and rename the extracted folder to `kafka`
2. Start ZooKeeper: `.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties`
3. Start the broker: `.\bin\windows\kafka-server-start.bat .\config\server.properties`
4. Create a topic: `.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092`
5. Produce/consume from the CLI using `kafka-console-producer.bat` / `kafka-console-consumer.bat`

---

## 7. Spring Boot & RabbitMQ Projects

**Path:** `spring-boot-rabbitmq-projects/`

Two separate RabbitMQ projects exploring producer/consumer patterns with Spring Boot using `spring-boot-starter-amqp`. Both require a running RabbitMQ broker (managed via Docker — see below).

### 7.1 RabbitMQ Demo App

**Path:** `spring-boot-rabbitmq-projects/rabbitmq-demo/`

Single-module demo showing a topic exchange with two queues — one for plain string messages, one for JSON (`Employee`) messages.

- **Tech stack:** Java 17, Spring Boot 3.5.x, Spring AMQP, Lombok, RabbitMQ 3 (Docker)
- **Base package:** `com.catmanscodes.rabbitmq`
- **Architecture:**
  - `config/RabbitMqConfig.java` — declares `springboot_app_exchange` (topic), two queues (`springboot_app_queue`, `springboot_app_employee_queue`), and bindings; registers `Jackson2JsonMessageConverter`
  - `service/RabbitMQProducerService.java` — publishes via `RabbitTemplate`
  - `service/RabbitMQConsumerService.java` — `@RabbitListener` consumers, logs received messages
- **Endpoints** (`/api/v1/publish`):
  | Method | Path | Description |
  |---|---|---|
  | GET | `/api/v1/publish?message=...` | Publish a plain string message |
  | POST | `/api/v1/publish` | Publish an `Employee` JSON message |
- **Broker:** `localhost:5672` (guest/guest), management UI at http://localhost:15672
- **API testing:** Postman collection under `rabbitmq-demo/POSTMAN/`

#### RabbitMQ setup (Docker)

A `docker-compose.yml` is included in `rabbitmq-demo/`:

```bash
docker compose up -d    # start RabbitMQ in the background
docker compose down     # stop
docker compose down -v  # stop and remove data volume
```

### 7.2 RabbitMQ Microservices

**Path:** `spring-boot-rabbitmq-projects/rabbitmq-microservices/`

Three independent Spring Boot services demonstrating a pub/sub flow: one producer (`user-service`) and two consumers (`email-service`, `sms-service`). No parent POM — each service has its own `pom.xml` and Maven wrapper.

- **Tech stack:** Java 17, Spring Boot 3.x, Spring AMQP, Lombok
- **Exchange:** `user_microservice_exchange` (topic), declared by `user-service`

| Service | Port | Role | Queue | Routing Key |
|---|---|---|---|---|
| `user-service` | 8081 | Producer | — | — |
| `sms-service` | 8082 | Consumer | `user_sms_queue` | `user_sms_service_key` |
| `email-service` | 8083 | Consumer | `user_email_queue` | `user_email_service_key` |

- **Endpoints** (all on `user-service` at port 8081):
  | Method | Path | Description |
  |---|---|---|
  | POST | `/api/v1/email` | Publish a `UserDto` message → `user_email_queue` |
  | POST | `/api/v1/sms` | Publish a `UserDto` message → `user_sms_queue` |
- **Flow:** `user-service` publishes to the exchange; `email-service` and `sms-service` receive the message asynchronously via `@RabbitListener` and log it
- **Broker:** `localhost:5672` (guest/guest) — start RabbitMQ (e.g. via the demo's `docker-compose.yml`) before running all three services

---

## 8. Spring Boot & Redis Projects

**Path:** `spring-boot-redis-projects/`

Projects exploring Redis with Spring Boot. `spring-boot-redis-projects/README.md` documents the
Windows/Docker Redis workflow (pull, run, `redis-cli`, Docker Compose, Redis Insight).

### 8.1 Weather App

**Path:** `spring-boot-redis-projects/weather-app/`

A small but production-oriented REST API for weather records, built to exercise most of the major
building blocks of a real Spring Boot service, using **MySQL** with a **Redis** cache layer.

- **Tech stack:** Java 17, Spring Boot 3.5.16, Spring Data JPA, Spring Data Redis, Flyway,
  MapStruct, Lombok, Bean Validation, springdoc-openapi (Swagger), Logback
- **Base package:** `com.catmanscodes.weatherapp`
- **Implemented features:**
  - CRUD with pagination; get by id and by city
  - **Flyway** migrations own the schema (`V1__create_weather_table.sql`); Hibernate `ddl-auto=validate`
  - **Redis cache-aside** (`@Cacheable`/`@CacheEvict`, 10-min TTL) on read endpoints
  - MapStruct entity↔DTO mapping, Lombok entity + Java record DTO, `@CreationTimestamp`/`@UpdateTimestamp`
  - Global exception handling (`404`/`409`/`400`/`500`) and Bean Validation
  - Logback console + rolling **error-only** file appender
  - OpenAPI/Swagger UI
- **Database:** MySQL `weather_db` (auto-created via `createDatabaseIfNotExist=true`; schema via Flyway, **not** `ddl-auto=update`)
- **Redis:** `localhost:6379` — start via the app's `docker-compose.yml` (`docker compose up -d`)
- **Endpoints** (`/api/v1/weather`, no auth yet):
  | Method | Path | Description |
  |---|---|---|
  | GET | `?page=&size=&sort=` | List records (paged) |
  | GET | `/{id}` | Get record by id (cached) |
  | GET | `/city/{city}` | Get record by city (cached) |
  | POST | `/create` | Create a record |
  | PUT | `/{id}` | Update a record |
  | DELETE | `/{id}` | Delete a record |
- **API docs:** http://localhost:8080/swagger-ui.html
- **API testing:** Postman collection at `spring-boot-redis-projects/weather-app/Postman/weather-app.postman_collection.json`
- **Status / roadmap:** Steps 1–9 are implemented and verified. **Out of scope for now** (to be resumed later): Spring Security (JWT), Actuator, full Dockerization (MySQL in compose + `Dockerfile`), tests, CI/CD, and AWS deployment. See the app's `PLAN.md` and `README.md`.

---

## Running an application

Each app is built and run independently using its own Maven wrapper, from inside the app's directory:

```bash
./mvnw clean install     # build (mvnw.cmd on native Windows shells)
./mvnw spring-boot:run   # run
```

All MySQL-backed apps expect a pre-created database (see per-project database name above) and use local default credentials (`root`/`root`). Schema is auto-managed via `spring.jpa.hibernate.ddl-auto=update` — no manual migrations needed. The one exception is **Weather App** (§8.1), which auto-creates its database and manages schema with **Flyway** migrations (`ddl-auto=validate`).
