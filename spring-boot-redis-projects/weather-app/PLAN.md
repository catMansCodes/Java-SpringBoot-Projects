# Weather App — Production-Readiness Build Plan

A small app that exercises almost all the major building blocks of a production-ready
Spring Boot service. Steps are ordered by build dependency so we can code layer-by-layer.

## Decisions
- **Logging:** Logback (Spring Boot default).
- **Extras folded in:** Spring Boot Actuator, Flyway migrations, list pagination.
- **Testing:** single dedicated final milestone (Step 14) — mandatory, never skipped.
- **Security:** JWT stateless, modeled on the `todo-apis` app.

## Corrections to make while building
1. `repository/WeatherRepository.java` is typed on the DTO record
   (`CrudRepository<WeatherDto, Long>`) — change to `JpaRepository<Weather, Long>` (Step 4).
2. Switch `spring.jpa.hibernate.ddl-auto` from `update` → `validate` once Flyway owns the schema (Step 2).

## Steps

### Foundation — domain & persistence
1. **Domain model** — finalize `Weather` entity (Lombok) + `WeatherDto` record; confirm columns/nullability. *(orig #8)*
2. **Persistence + Flyway** — MySQL/JPA/Hibernate config; add Flyway, `db/migration/V1__create_weather_table.sql` matching the entity, set `ddl-auto=validate`. *(orig #3 + Flyway)*
3. **MapStruct mapper** — verify/extend entity↔DTO mapping. *(orig #5)*
4. **CRUD** — implement `WeatherServiceImpl` + controller: `findAll` (with pagination/`Pageable`), `findById`, `findByCity`, `addWeather`, `updateWeather`, `deleteById`; fix `WeatherRepository` to `JpaRepository<Weather, Long>`. *(orig #1 + pagination)*

### Robustness — errors, validation, observability
5. **Global exception handling** — throw `WeatherNotFoundException` from service; extend `GlobalExceptionHandler` (incl. validation errors → `ErrorResponse`). *(orig #6)*
6. **Bean validation** — jakarta constraints on `WeatherDto`, `@Valid` in controller, `MethodArgumentNotValidException` handler. *(orig #9)*
7. **Logging (Logback)** — `logback-spring.xml`: console + rolling error-log file appender. *(orig #4)*
8. **API docs (Swagger)** — springdoc-openapi-starter-webmvc-ui, annotate controller/DTO, expose Swagger UI. *(orig #7)*

### Caching, security, ops
9. **Redis caching** — `RedisConfig` (serializers, TTL); cache-aside via `@Cacheable` on reads, `@CacheEvict`/`@CachePut` on writes. *(orig #10)*

---

# Out of scop for now
10. **Spring Security (JWT stateless)** — port `todo-apis` pattern: token provider, auth filter, entry point, `SecurityConfig`; ADMIN = create/update/delete, USER = read; login/auth endpoint. *(orig #2)*
11. **Actuator** — add starter, expose `health`/`info`/`metrics`; permit `health` through security. *(extra)*
12. **Dockerization** — uncomment MySQL in `docker-compose.yml`, run Redis + MySQL together; add app `Dockerfile`. *(orig #11)*

### Delivery
13. **Testing (mandatory, not skipped)** — JUnit 5 + Mockito unit tests (service, mapper) and `@SpringBootTest`/MockMvc integration tests (controller CRUD, validation, security roles). *(orig #12)*
14. **CI/CD — GitHub Actions** — workflow: build + run tests (fail on test failure), optional code-review. *(orig #13)*
15. **AWS deployment** — containerized deploy; Actuator `health` as the health check. *(orig #14)*

> All 14 original features preserved and reordered; Flyway + pagination folded into Steps 2/4; Actuator added as Step 11. Testcontainers intentionally excluded.
