# RabbitMQ Microservices

A small demo of a RabbitMQ pub/sub flow across three independent Spring Boot 3 (Java 17) microservices: one producer (`user-service`) and two consumers (`sms-service`, `email-service`).

## RabbitMQ Setup

- **Exchange**: `user_microservice_exchange` — a `TopicExchange`, declared only in `user-service` (`config/UserRabbitMQConfig.java`). The consumer services don't redeclare the exchange/bindings; they just listen on the queue name they're configured with, so `user-service` must run at least once to create the topology.
- **Message format**: JSON, via `Jackson2JsonMessageConverter` on both the producer (`RabbitTemplate`) and consumer (`@RabbitListener`) sides. Each service has its own local `UserDto` copy (no shared library) — keep their fields in sync manually if the message shape changes.
- **Broker**: local RabbitMQ, default connection `localhost:5672`, credentials `guest`/`guest` (see each service's `application.properties`).

**Queues & routing keys:**

| Queue | Routing key | Consumer |
| --- | --- | --- |
| `user_sms_queue` | `user_sms_service_key` | `sms-service` |
| `user_email_queue` | `user_email_service_key` | `email-service` |

### Architecture

```
                 POST /api/v1/email                POST /api/v1/sms
                        |                                  |
                        v                                  v
                 +------------------------------------------------+
                 |                 user-service (8081)             |
                 |         producer -> RabbitTemplate.convertAndSend|
                 +------------------------------------------------+
                        |                                  |
                 routing key:                       routing key:
              user_email_service_key            user_sms_service_key
                        |                                  |
                        v                                  v
        +-------------------------------+     user_microservice_exchange
        |   user_microservice_exchange  |<--- (TopicExchange, declared
        |         (TopicExchange)       |      by user-service)
        +-------------------------------+
                        |                                  |
                        v                                  v
              +-------------------+              +-------------------+
              | user_email_queue  |              |  user_sms_queue   |
              +-------------------+              +-------------------+
                        |                                  |
                        v                                  v
              +-------------------+              +-------------------+
              |  email-service    |              |   sms-service     |
              |     (8083)        |              |     (8082)        |
              |  @RabbitListener  |              |  @RabbitListener  |
              +-------------------+              +-------------------+
```

`user-service` publishes to the exchange with a routing key; the exchange routes the message to whichever queue is bound with a matching key; the corresponding consumer service picks it up via `@RabbitListener` and logs it (no further processing in this demo).

## Project Details

Producer : user service
Consumer : sms, email & notification service

| Service | Port | Role |
| --- | --- | --- |
| `user-service` | 8081 | Producer — exposes REST endpoints, publishes to RabbitMQ |
| `sms-service` | 8082 | Consumer — listens on `user_sms_queue` |
| `email-service` | 8083 | Consumer — listens on `user_email_queue` |

Each service is an independent Maven project (own `pom.xml` / wrapper, no parent POM) — build/run them individually with `./mvnw spring-boot:run` from within each directory. Start RabbitMQ first, then start all three services before calling the endpoints below.

## Endpoints

Both endpoints are exposed by `user-service` (port 8081). They publish the request body to RabbitMQ and return it as-is; actual SMS/email handling happens asynchronously in `sms-service`/`email-service` (currently just logged there).

### Send Email

```
POST http://localhost:8081/api/v1/email
Content-Type: application/json

{
  "id": 1,
  "userName": "catman",
  "email": "catman@example.com",
  "phoneNumber": "9999999999"
}
```

Routed to the `user_email_queue` and consumed by `email-service`.

### Send SMS

```
POST http://localhost:8081/api/v1/sms
Content-Type: application/json

{
  "id": 1,
  "userName": "catman",
  "email": "catman@example.com",
  "phoneNumber": "9999999999"
}
```

Routed to the `user_sms_queue` and consumed by `sms-service`.