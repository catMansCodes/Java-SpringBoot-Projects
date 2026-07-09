# Weather App

A small but production-oriented REST API for weather records, built to exercise most of the
major building blocks of a real Spring Boot 3 service. Backed by **MySQL** with a **Redis**
cache layer.

- **Tech stack:** Java 17, Spring Boot 3.5.16, Spring Data JPA, Spring Data Redis, Flyway,
  MapStruct, Lombok, Bean Validation, springdoc-openapi (Swagger), Logback
- **Base package:** `com.catmanscodes.weatherapp`
- **Layering:** `controller → service (+impl) → repository`, with `dto`, `entity`, `mapper`,
  `exception`, and `config` packages alongside

> **Status:** Steps 1–9 below are implemented and verified end-to-end. Security and the
> remaining ops/delivery steps are **out of scope for now** and will be resumed later
> (see [Roadmap](#roadmap-out-of-scope-for-now)).

---

## Implemented

| # | Area | Notes |
|---|------|-------|
| 1 | **Domain model** | `Weather` entity (Lombok) + `WeatherDto` record; `city` unique, auditing `createdAt`/`updatedAt` (`@CreationTimestamp`/`@UpdateTimestamp`) |
| 2 | **Persistence + Flyway** | MySQL `weather_db`; schema owned by Flyway (`V1__create_weather_table.sql`), Hibernate runs in `validate` mode |
| 3 | **MapStruct mapper** | entity ↔ DTO mapping, `unmappedTargetPolicy = ERROR`, in-place update mapping |
| 4 | **CRUD** | paginated list (`Pageable`), get by id / by city, create, update, delete |
| 5 | **Global exception handling** | `404` not found, `409` duplicate city, `400` validation / malformed body, logged generic `500` |
| 6 | **Bean validation** | jakarta constraints on `WeatherDto` (`@NotBlank`, `@Size`, `@NotNull`, `@Min`/`@Max`), `@Valid` on write endpoints; field-level errors in the response |
| 7 | **Logging** | Logback (`logback-spring.xml`): console + rolling **error-only** file appender (`logs/`) |
| 8 | **API docs** | springdoc-openapi; Swagger UI at `/swagger-ui.html` |
| 9 | **Redis caching** | cache-aside: `@Cacheable` reads (`weatherById`, `weatherByCity`), evict on writes, 10-min TTL |

---

## API

Base path: `/api/v1/weather` (no authentication yet — see Roadmap).

| Method | Path | Description | Success |
|---|---|---|---|
| GET | `/api/v1/weather?page=0&size=10&sort=id,asc` | List records (paged) | 200 |
| GET | `/api/v1/weather/{id}` | Get record by id (cached) | 200 |
| GET | `/api/v1/weather/city/{city}` | Get record by city (cached) | 200 |
| POST | `/api/v1/weather/create` | Create a record | 201 |
| PUT | `/api/v1/weather/{id}` | Update a record | 200 |
| DELETE | `/api/v1/weather/{id}` | Delete a record | 200 |

Error responses use a common `ErrorResponse` shape: `404` (not found), `409` (duplicate city),
`400` (validation / malformed body — includes a `validationErrors` map), `500` (unexpected).

**Example create body:**

```json
{
  "city": "London",
  "details": "Cloudy",
  "temperature": 14.5,
  "humidity": 80
}
```

- **API docs:** http://localhost:8080/swagger-ui.html
- **Postman:** `Postman/weather-app.postman_collection.json`

---

## Running locally

### 1. Redis (required)

A `docker-compose.yml` is included (Redis enabled; MySQL commented out):

```bash
docker compose up -d     # start Redis (localhost:6379)
docker compose down      # stop
```

### 2. MySQL (required)

Run MySQL locally on `localhost:3306` with `root`/`root`. The database `weather_db` is
created automatically (`createDatabaseIfNotExist=true`) and its schema is applied by Flyway on
startup — no manual `CREATE TABLE` needed.

### 3. App

```bash
./mvnw spring-boot:run     # mvnw.cmd on native Windows shells
```

Defaults to `server.port=8080`.

---

## Roadmap (out of scope for now)

These steps are planned and paused; they will be resumed later. See `PLAN.md` for full detail.

| # | Area | Status |
|---|------|--------|
| 10 | **Spring Security (JWT stateless)** — ADMIN write / USER read, login endpoint (modeled on `todo-apis`) | ⏸️ Deferred |
| 11 | **Actuator** — health/info/metrics | ⏸️ Deferred |
| 12 | **Dockerization** — add MySQL to compose + app `Dockerfile` | ⏸️ Deferred |
| 13 | **Testing** — JUnit 5 + Mockito unit tests, `@SpringBootTest`/MockMvc integration tests | ⏸️ Deferred |
| 14 | **CI/CD** — GitHub Actions (build + test) | ⏸️ Deferred |
| 15 | **AWS deployment** | ⏸️ Deferred |
