## Запуск Kafka и Zookeeper из Docker, знакомство с базовыми командами

1. Устанавливаем Docker Compose (1 Zookeeper, 1 Broker):

       docker-compose up -d
2. Заходим на брокер:

       docker exec -it kafka bash
3. Создаем новый топик используя `--create`:

       kafka-topics --bootstrap-server kafka:9092 --create --topic demo-topic
4. Выводим список существующих топиков с помощью команды `--list`:

       kafka-topics --bootstrap-server kafka:9092 --list 
5. Опция `--describe` возвращает информацию о топике:

       kafka-topics --bootstrap-server kafka:9092 --describe --topic demo-topic
6. Запускаем producer:

       kafka-console-producer --bootstrap-server kafka:9092 --topic demo-topic
7. Запускаем consumer:

       kafka-console-consumer --bootstrap-server kafka:9092 --topic demo-topic --from-beginning
8. Эксперементируем с количеством partitions и с количеством consumers в consumer group:

       kafka-topics --bootstrap-server kafka:9092 --alter --topic demo-topic --partitions 3
       kafka-topics --bootstrap-server kafka:9092 --describe --topic demo-topic
9. Запускаем следующую команду в 4х разных консолях одновременно, чтобы увидеть, что для одного consumer не хватило partition:

       kafka-console-consumer --bootstrap-server kafka:9092 --topic demo-topic --group demo-group
