package hardsoftskills.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
        final String TOPIC_NAME = "demo-topic";
        final String GROUP_ID = "group2";

        // Initialize Logger
        Logger logger = LoggerFactory.getLogger(Consumer.class);

        // Create Consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        try {
            // Subscribing on topic
            consumer.subscribe(Collections.singleton(TOPIC_NAME));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    logger.info("key: " + record.key()
                            + " value: " + record.value()
                            + " partition: " + record.partition()
                            + " offset: " + record.offset());
                }
            }
        } catch (Exception err) {
            logger.error("Unexpected exception", err);
        } finally {
            consumer.close();
            logger.info("The consumer was closed");
        }
    }
}
