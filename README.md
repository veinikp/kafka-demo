# kafka-demo

https://habr.com/ru/post/543732/ 

Создаем сеть, 1 zookeeper, 1 брокер:
docker network create kafkanet
docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

Заходим на брокер:
docker exec -it kafka bash

Создаем топик, выводим список топиков, запускаем Kafka CLI producer и consumer:
/bin/kafka-topics --create --topic demo-topic --bootstrap-server kafka:9092
/bin/kafka-topics --list --zookeeper zookeeper:2181
/bin/kafka-topics --describe --topic demo-topic --bootstrap-server kafka:9092
/bin/kafka-console-producer --topic demo-topic --bootstrap-server kafka:9092
/bin/kafka-console-consumer --topic demo-topic --from-beginning --bootstrap-server kafka:9092

Играем с количеством partitions и с количеством consumers в consumer group:
/bin/kafka-topics --zookeeper zookeeper:2181 --alter --topic demo-topic --partitions 3
/bin/kafka-topics --describe --topic demo-topic --bootstrap-server kafka:9092

Это надо запустить в 4х разных консолях одновременно чтобы увидеть что для одного consumer не хватило partition:
docker exec -it kafka /bin/kafka-console-consumer --topic demo-topic --group demo-group --bootstrap-server kafka:9092



