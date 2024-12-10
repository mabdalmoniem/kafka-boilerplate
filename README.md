# kafka-boilerplate
A docker-compose file to create a single node Kafka cluster managed by Zookeeper

# Docker commands:

### 1. **Create a Topic with N Partitions**
Create a new Kafka topic with a specified number of partitions and a replication factor of 1.

```bash
docker exec -it hello-kafka-kafka-1 kafka-topics --create --topic <topic_name> --partitions <n> --replication-factor 1 --bootstrap-server localhost:29092
```


### 2. **List all topics**
List all topics available in the Kafka instance.


```bash
docker exec -it hello-kafka-kafka-1 kafka-topics --list --bootstrap-server localhost:29092
```

### 3. **Post a Message to a Topic**
Send a message (with JSON payload) to a specific Kafka topic.


```bash
echo '{"id": 1, "name": "mohamed", "email": "mohamed@github.com"}' | docker exec -i hello-kafka-kafka-1 kafka-console-producer --topic <topic_name> --bootstrap-server localhost:29092
```

### 4. **Consume Messages from a Topic**
Consume messages from a Kafka topic starting from the beginning.

```bash
docker exec -it hello-kafka-kafka-1 kafka-console-consumer --topic <topic_name> --bootstrap-server localhost:29092 --from-beginning
```

using `max-messages`
```bash
docker exec -it hello-kafka-kafka-1 kafka-console-consumer --topic user_created --bootstrap-server localhost:29092 --from-beginning --max-messages <number_of_messages>
```