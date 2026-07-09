# Redis Installation using Docker (Windows)

This guide walks through installing and running **Redis** on Windows using **Docker Desktop**. This is the recommended approach because it closely resembles production environments and avoids installing Redis directly on Windows.

---

# Prerequisites

Before starting, make sure you have the following installed:

- ✅ Docker Desktop
- ✅ Docker Desktop is running
- ✅ Docker CLI is accessible from your terminal

Verify Docker installation:

```bash
docker --version
```

Example:

```text
Docker version 28.x.x
```

---

# Pull the Redis Docker Image

Download the latest Redis image from Docker Hub.

```bash
docker pull redis:latest
```

Verify the image has been downloaded:

```bash
docker images
```

Example output:

```text
REPOSITORY    TAG       IMAGE ID
redis         latest    xxxxxxxxx
```

---

# Run Redis Container

Start a Redis container.

```bash
docker run -d --name redis-server -p 6379:6379 redis:latest
```

### Command Explanation

| Option | Description |
|----------|-------------|
| `-d` | Run the container in detached (background) mode |
| `--name redis-server` | Assign a custom container name |
| `-p 6379:6379` | Map Redis port 6379 from the container to the host machine |
| `redis:latest` | Redis Docker image |

---

# Verify Redis Container

Check whether the Redis container is running.

```bash
docker ps
```

Example output:

```text
CONTAINER ID   IMAGE           PORTS
xxxxxxxxxxx    redis:latest    0.0.0.0:6379->6379/tcp
```

---

# Connect to Redis

Open the Redis CLI inside the running container.

```bash
docker exec -it redis-server redis-cli
```

Successful connection:

```text
127.0.0.1:6379>
```

---

# Verify Redis Installation

Run the following command:

```bash
PING
```

Output:

```text
PONG
```

If you receive **PONG**, Redis is running successfully.

---

# Basic Redis Commands

### Store a Value

```bash
SET name Vimal
```

Output

```text
OK
```

---

### Retrieve a Value

```bash
GET name
```

Output

```text
"Vimal"
```

---

### Delete a Value

```bash
DEL name
```

Output

```text
(integer) 1
```

---

# Exit Redis CLI

```bash
QUIT
```

or

```bash
exit
```

---

# Docker Container Management

## Stop Redis

```bash
docker stop redis-server
```

---

## Start Redis

```bash
docker start redis-server
```

---

## Restart Redis

```bash
docker restart redis-server
```

---

## Remove Redis Container

```bash
docker rm -f redis-server
```

> **Note:** This removes only the container. The Redis Docker image remains on your machine.

---

## Remove Redis Image

```bash
docker rmi redis:latest
```

---

# Running Redis with Docker Compose (Recommended)

Instead of using `docker run`, it's recommended to use Docker Compose for better maintainability.

Create a file named:

```
docker-compose.yml
```

```yaml
services:
  redis:
    image: redis:7.2
    container_name: redis-server

    ports:
      - "6379:6379"

    restart: unless-stopped

    volumes:
      - redis-data:/data

volumes:
  redis-data:
```

---

## Start Redis

```bash
docker compose up -d
```

---

## Stop Redis

```bash
docker compose down
```

---

# Why Use Docker Volumes?

## Without Volume

```text
Stop Container
      │
      ▼
Remove Container
      │
      ▼
All Redis Data Lost
```

---

## With Volume

```text
Redis Container
        │
        ▼
Docker Volume
        │
        ▼
Data Persists
```

Using a Docker volume ensures Redis data survives container removal and recreation. This becomes especially important when working with **Redis Persistence (RDB/AOF)**.

---

# Useful Docker Commands

## View Container Logs

```bash
docker logs redis-server
```

---

## Follow Live Logs

```bash
docker logs -f redis-server
```

---

## Inspect Container Details

```bash
docker inspect redis-server
```

---

## List Running Containers

```bash
docker ps
```

---

## List All Containers

```bash
docker ps -a
```

---

## List Docker Images

```bash
docker images
```

---

# Redis GUI Tool (Recommended)

Although `redis-cli` is useful for learning and debugging, working with large amounts of data becomes difficult.

The recommended GUI is:

## Redis Insight (Official)

Redis Insight allows you to:

- Browse Redis keys
- Edit key values
- View TTL
- Execute Redis commands
- Inspect Hashes
- Inspect Lists
- Inspect Sets
- Inspect Sorted Sets
- View Streams
- Monitor memory usage
- Analyze Redis performance

It is the official GUI provided by Redis and is highly recommended for local development.

---

# Quick Reference

| Task | Command |
|------|---------|
| Pull Redis Image | `docker pull redis:latest` |
| Run Redis | `docker run -d --name redis-server -p 6379:6379 redis:latest` |
| View Running Containers | `docker ps` |
| Open Redis CLI | `docker exec -it redis-server redis-cli` |
| Test Connection | `PING` |
| Stop Redis | `docker stop redis-server` |
| Start Redis | `docker start redis-server` |
| Restart Redis | `docker restart redis-server` |
| Remove Container | `docker rm -f redis-server` |
| Remove Image | `docker rmi redis:latest` |
| Docker Compose Start | `docker compose up -d` |
| Docker Compose Stop | `docker compose down` |
| View Logs | `docker logs redis-server` |
| Follow Logs | `docker logs -f redis-server` |
| Inspect Container | `docker inspect redis-server` |

---

# Next Steps

Now that Redis is running successfully, the next step is to integrate it with a **Spring Boot 3 + Java 17** application.

We'll cover:

1. Redis Configuration in Spring Boot
2. `RedisTemplate`
3. `StringRedisTemplate`
4. Caching with `@Cacheable`
5. `@CachePut`
6. `@CacheEvict`
7. TTL Configuration
8. Building a Weather API using Redis Cache
