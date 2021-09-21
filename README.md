## Springboot kafka messages

Example spring-boot communication via kafka broker

## Modules

| Name              | Ports         | Description                   |
| :---              | :---          | :---                          |
| `kafka-message`   | `-`           | Format message as json object |
| `kafka-producer`  | `8081/tcp`    | Sending message               |
| `kafka-listener`  | `Random/tcp`  | Receiving message using listener method   |
| `kafka-consumer`  | `8082/tcp`    | Replying from `kafka-producer` message |


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
4. Run module `kafka-producer`, using command:
   ```bash
   mvn clean -DskipTests -pl kafka-producer spring-boot:run 
   ```
5. Run module `kafka-listner`, using command:
   ```bash
   mvn clean -DskipTests -pl kafka-listener spring-boot:run
   ```

References:

- https://docs.spring.io/spring-kafka/docs/2.7.7/reference/html/
- https://www.baeldung.com/spring-kafka
- https://kafka.apache.org/
- https://hub.docker.com/r/confluentinc/cp-kafka/
- https://hub.docker.com/r/confluentinc/cp-zookeeper
