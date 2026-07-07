# RabbitMQ Demo (Spring Boot 3)

A simple Spring Boot 3 / Java 17 demo showing how to publish and consume messages with RabbitMQ using `spring-boot-starter-amqp`. It demonstrates:

- Sending a plain string message
- Sending a JSON object (`Employee`) message
- A topic exchange with two queues, each bound with its own routing key

## Tech stack

- Java 17
- Spring Boot 3.5.x (Web, AMQP)
- Lombok
- RabbitMQ 3 (management image, via Docker)

## Prerequisites

- JDK 17+
- Docker Desktop (or a Docker engine) running locally

## RabbitMQ setup with Docker (step by step)

This project ships a `docker-compose.yml` that runs RabbitMQ with the management plugin enabled.

### 1. Review the compose file

`docker-compose.yml`:

```yaml
version: "3.9"

services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    restart: unless-stopped
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    volumes:
      - rabbitmq_data:/var/lib/rabbitmq

volumes:
  rabbitmq_data:
```

- `5672` → AMQP port (used by the Spring Boot app to connect)
- `15672` → Management UI port (web dashboard)
- `RABBITMQ_DEFAULT_USER` / `RABBITMQ_DEFAULT_PASS` → credentials, must match `application.properties`
- `rabbitmq_data` → named volume so queues/messages survive container restarts

### 2. Start RabbitMQ

From this project's root directory (where `docker-compose.yml` lives):

```bash
docker compose up -d
```

`-d` runs it in the background (detached mode).

### 3. Verify the container is running

```bash
docker ps
```

You should see a container named `rabbitmq` with status `Up`.

### 4. Open the management UI

Visit [http://localhost:15672](http://localhost:15672) and log in with:

- Username: `guest`
- Password: `guest`

From here you can inspect exchanges, queues, bindings, and messages in real time.

### 5. Confirm application config matches

`src/main/resources/application.properties` connects using the same host/port/credentials:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 6. Run the Spring Boot application

```bash
./mvnw spring-boot:run
```

On startup, `RabbitMqConfig` auto-declares the exchange, queues, and bindings — no manual queue creation needed in the UI.

### 7. Test the endpoints

Send a plain string message:

```bash
curl "http://localhost:8080/api/v1/publish?message=Hello RabbitMQ"
```

Send an `Employee` JSON message:

```bash
curl -X POST http://localhost:8080/api/v1/publish \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"John Doe\",\"department\":\"Engineering\",\"salary\":75000}"
```

Check the application logs for `RabbitMQConsumerService` output confirming the message was received, or check the **Queues** tab in the management UI to see message counts.

### 8. Stop RabbitMQ

```bash
docker compose down
```

Add `-v` to also remove the `rabbitmq_data` volume (this deletes all queued data):

```bash
docker compose down -v
```

## Basic Docker commands

| Description | Command |
|---|---|
| Start services defined in `docker-compose.yml` in the background | `docker compose up -d` |
| Stop and remove containers/networks created by compose | `docker compose down` |
| Stop and remove containers, networks, and volumes | `docker compose down -v` |
| View logs from the compose services (follow mode) | `docker compose logs -f` |
| View logs for a specific container | `docker logs -f rabbitmq` |
| List running containers | `docker ps` |
| List all containers, including stopped ones | `docker ps -a` |
| Start a stopped container | `docker start rabbitmq` |
| Stop a running container | `docker stop rabbitmq` |
| Restart a container | `docker restart rabbitmq` |
| Remove a stopped container | `docker rm rabbitmq` |
| Open a shell inside the running container | `docker exec -it rabbitmq bash` |
| List Docker images on this machine | `docker images` |
| Pull an image from Docker Hub | `docker pull rabbitmq:3-management` |
| Remove an image | `docker rmi rabbitmq:3-management` |
| List Docker volumes | `docker volume ls` |
| Remove a specific volume | `docker volume rm rabbitmq-demo_rabbitmq_data` |
| Show resource usage (CPU/memory) of running containers | `docker stats` |

## Project structure

```
src/main/java/com/catmanscodes/rabbitmq
├── config/RabbitMqConfig.java          # Exchange, queues, bindings, message converter
├── controller/ProducerController.java  # REST endpoints to publish messages
├── dto/Employee.java                   # Sample message payload
├── service/RabbitMQProducerService.java # Publishes messages via RabbitTemplate
└── service/RabbitMQConsumerService.java # @RabbitListener consumers
```

## Configuration reference

| Property | Purpose |
|---|---|
| `rabbitmq.exchange.name` | Topic exchange name (`springboot_app_exchange`) |
| `rabbitmq.queue.name` | Queue for plain string messages |
| `rabbitmq.binding.routing.key` | Routing key for the string queue |
| `rabbitmq.queue.employee.name` | Queue for `Employee` object messages |
| `rabbitmq.employee.binding.routing.key` | Routing key for the employee queue |