# Log backup

API for storing logs in the file system. By default, the log files will be created in the root of the app folder

### Prerequisites

You must have docker install to run the RabbitMQ image, which is required to run the project

```
docker run -d -p 5672:5672 -p 15672:15672 --hostname my-rabbit --name some-rabbit rabbitmq:3
```

### Start the application

```
mvn spring-boot:run
```

### Calling Log api

Once the application is started, we can start posting logs to the API

```
curl -X POST -H 'Content-Type: application/json' -d '{"content": "this is what happened"}' http://localhost:8080/log
```

### Running the tests

To run the load test execute the command below. Make sure that the application is running

```
mvn gatling:test
```