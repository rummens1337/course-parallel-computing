# Distributed version of quicksort

## Prerequisites
### Java
Java installation similar to ``java --version`` output below.
```bash
java 15.0.1 2020-10-20
Java(TM) SE Runtime Environment (build 15.0.1+9-18)
Java HotSpot(TM) 64-Bit Server VM (build 15.0.1+9-18, mixed mode, sharing)
```

### Gradle
Have gradle version ~ Gradle 4.4.1 installed locally.

### ActiveMQ
Either use the Docker Compose command, or run the Docker container manually.

#### Automatically
You can make use of the docker-compose (with increased memory). Simply execute command below.
```bash
docker-compose -f docker-compose.yml up --build
```

#### Manually
This application makes use of Apache's ActiveMQ, which is available in Docker.
To pull the docker container and run it, execute the following commands.
Found on: https://hub.docker.com/r/rmohr/activemq
```bash
docker pull rmohr/activemq
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```
After running the docker container, the ActiveMQ server will be available in your browser under http://127.0.0.1:8161/admin/.

### Jar files for ActiveMQ
ActiveMQ requires a bunch of Jar files to work properly. IntelliJ (most likely any IDE) will give 
recommendations for downloading them.