## Springboot kafka messages

Example spring-boot communication via kafka broker

## Model / Interface

| Name              | Ports         | Description                   |
| :---              | :---          | :---                          |
| `kafka-message`   | `-`           | Format message as json object |

## Modules 1

Scenario sending & receive message as json

| Name              | Ports         | Description                   |
| :---              | :---          | :---                          |
| `kafka-producer`  | `8081/tcp`    | Sending message               |
| `kafka-listener`  | `Random/tcp`  | Receiving message using listener method |
| `kafka-consumer`  | `Random/tcp`  | Receiving message dif id same topic     |


## Module 2

Scenario sending & reply message as string message

| Name              | Ports         | Description                             |
| :---              | :---          | :---                                    |
| `kafka-send-reply`| `8082/tcp`    | Send and Reply message in same module   |


## How to run

1. Downloads dependencies using command:
   ```bash
   mvn clean -DskipTests package
   ```
2. Install dependency package `kafka-message`, using command:
   ```bash
   mvn clean -DskipTests -pl kafka-message -am install
   ```
3. Running kafka cluster in docker, using `docker-compose` command:
   ```bash
   docker-compose -p dev --env-file .env up -d
   ```
4. Run springboot per module, using command:
   ```bash
   mvn clean -DskipTests -pl <kafka-producer | kafka-listener | kafka-consumer> spring-boot:run 
   ```

References:

- https://docs.spring.io/spring-kafka/docs/2.7.7/reference/html/
- https://www.baeldung.com/spring-kafka
- https://kafka.apache.org/
- https://hub.docker.com/r/confluentinc/cp-kafka/
- https://hub.docker.com/r/confluentinc/cp-zookeeper
