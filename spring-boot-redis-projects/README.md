Prerequisites

Make sure you already have:

✅ Docker Desktop installed
✅ Docker Desktop running
✅ docker command working

Verify:

docker --version

Example:

Docker version 28.x.x
Pull the Redis Image

Download the latest Redis image:

docker pull redis:latest

Check the downloaded image:

docker images

Example:

REPOSITORY   TAG       IMAGE ID

redis        latest    xxxxxxxxx
Run Redis

The simplest command:

docker run -d \
  --name redis-server \
  -p 6379:6379 \
  redis:latest

On Windows PowerShell or CMD, use a single line:

docker run -d --name redis-server -p 6379:6379 redis:latest

Explanation:

Option	Meaning
-d	Run in background
--name redis-server	Container name
-p 6379:6379	Map Redis port
redis:latest	Redis image

Check that it's running:

docker ps

You should see something like:

CONTAINER ID

redis-server

0.0.0.0:6379->6379
Verify Redis

Enter the container:

docker exec -it redis-server redis-cli

Now you're inside Redis:

127.0.0.1:6379>

Test it:

PING

Output:

PONG

Congratulations—Redis is running.

Try Some Commands

Store a value:

SET name Vimal

Read it:

GET name

Output:

"Vimal"

Delete it:

DEL name
Exit Redis CLI
QUIT

Exit the container shell:

exit
Stop Redis
docker stop redis-server
Start Again
docker start redis-server
Restart
docker restart redis-server
Remove Container
docker rm -f redis-server

This removes only the container, not the downloaded image.

Remove Image
docker rmi redis:latest
A Better Setup: Docker Compose

Instead of docker run, I usually create a docker-compose.yml.

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

Start it:

docker compose up -d

Stop it:

docker compose down
Why use a Volume?

Without a volume:

Stop Container

↓

Remove Container

↓

Everything Lost

With a volume:

Redis

↓

Docker Volume

↓

Data Survives

This becomes important when we discuss persistence (RDB/AOF).

Useful Docker Commands

Check logs:

docker logs redis-server

Follow logs:

docker logs -f redis-server

Container details:

docker inspect redis-server
GUI Tools (Highly Recommended)

Working only with redis-cli becomes tedious. I recommend using a GUI.

Redis Insight (Official)

This is Redis's official desktop GUI.

It lets you:

Browse keys
Edit values
View TTL
Monitor memory
Run commands
Inspect Streams
View Hashes, Lists, Sets, etc.

It's the tool I recommend for development.