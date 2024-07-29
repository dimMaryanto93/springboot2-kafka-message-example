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

## Module 3

Scenario sending & reply message as json message

| Name                     | Ports         | Description                            |
| :---                     | :---          | :---                                   |
| `kafka-send-reply-json`  | `8083/tcp`    | Send and Reply message json format     |

### Module 4 

Scenario sending & received message with database transaction

| Name                     | Ports         | Description                            |
| :---                     | :---          | :---                                   |
| `kafka-transaction`      | `8084/tcp`    | Send and Reply message json format     |

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
   docker compose -p dev --env-file .env up -d
   ```
4. Run springboot per module, using command:
   ```bash
   mvn clean -DskipTests -pl <module-name> spring-boot:run 
   ```

References:

- https://docs.spring.io/spring-kafka/docs/2.7.7/reference/html/
- https://www.baeldung.com/spring-kafka
- https://kafka.apache.org/
- https://hub.docker.com/r/confluentinc/cp-kafka/
- https://hub.docker.com/r/confluentinc/cp-zookeeper
