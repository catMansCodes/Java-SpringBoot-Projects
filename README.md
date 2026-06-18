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

### Local Kafka setup (Windows)

Documented in `spring-boot-kafka-projects/README.md`:

1. Download Kafka (`kafka_2.13-3.7.0`) and rename the extracted folder to `kafka`
2. Start ZooKeeper: `.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties`
3. Start the broker: `.\bin\windows\kafka-server-start.bat .\config\server.properties`
4. Create a topic: `.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092`
5. Produce/consume from the CLI using `kafka-console-producer.bat` / `kafka-console-consumer.bat`

---

## Running an application

Each app is built and run independently using its own Maven wrapper, from inside the app's directory:

```bash
./mvnw clean install     # build (mvnw.cmd on native Windows shells)
./mvnw spring-boot:run   # run
```

All MySQL-backed apps expect a pre-created database (see per-project database name above) and use local default credentials (`root`/`root`). Schema is auto-managed via `spring.jpa.hibernate.ddl-auto=update` — no manual migrations needed.
