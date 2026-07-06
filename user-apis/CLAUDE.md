# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This file covers `user-apis` specifically. See the repo-root `CLAUDE.md` (one level up) for monorepo-wide conventions shared across all apps.

## What this app is

A standalone REST CRUD API for managing users (`com.catmanscodes.userapis`), built with Spring Boot 3.5.15 / Java 17. It is conceptually consumed by `todo-apis` (no actual runtime dependency — both apps are independent Maven builds).

## Commands

Run from inside this directory (`user-apis/`):

```bash
./mvnw clean install              # build (mvnw.cmd on native Windows shells)
./mvnw spring-boot:run             # run — serves on http://localhost:8080
./mvnw test                        # run all tests
./mvnw test -Dtest=ClassName       # run a single test class
./mvnw test -Dtest=ClassName#methodName   # run a single test method
```

There is currently only the default `UserApisApplicationTests` context-load smoke test — no controller/service/repository tests exist yet.

## Architecture

Standard layered structure: `controller -> service (interface + impl) -> repository (Spring Data JPA)`, with `dto`, `model`, `utils` (mapper), and `exception` packages alongside.

- `controller/UserController` — exposes `/api/v1/users` REST endpoints (GET all, GET by id, POST, PUT, DELETE), delegates entirely to `UserService`.
- `service/UserService` (interface) + `service/UserServiceImpl` — business logic; throws `ResourceNotFoundException` when a user id doesn't exist.
- `repository/UserRepository` — Spring Data JPA repository over `model/User`.
- `utils/UserMapper` — manual conversion between `model/User` entity and `dto/UserDto`.
- `exception/GlobalExceptionHandler` (`@ControllerAdvice`) — maps `ResourceNotFoundException` to 404 and any other `Exception` to 500, both returned as a structured `ErrorResponse` (`statusCode`, `errorMessage`, `timestamp`).

`User.email` is unique and not-nullable, enforced at the database level via the entity mapping (schema is auto-managed by `spring.jpa.hibernate.ddl-auto=update`, not migration scripts).

## Database

- MySQL, database name `user`, must be pre-created before running.
- Connection/credentials in `src/main/resources/application.properties` (local defaults `root`/`root`, `jdbc:mysql://localhost:3306/user`).
- `spring.profiles.active=dev` is set; no separate `application-dev.properties` currently exists, so it's effectively a no-op placeholder.

## API testing

A Postman collection lives under `Postman/` for manual exercising of the endpoints. Full request/response examples and the error-response shape are documented in `README.md`.
