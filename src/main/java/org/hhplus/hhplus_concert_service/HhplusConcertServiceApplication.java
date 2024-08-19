package org.hhplus.hhplus_concert_service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Properties;
import java.util.concurrent.Future;

@ServletComponentScan
@EnableCaching
@SpringBootApplication
public class HhplusConcertServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(HhplusConcertServiceApplication.class, args);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//        try {
//            // 메시지 전송
//            ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "key", "Hello Kafka!");
//            Future<RecordMetadata> future = producer.send(record);
//            RecordMetadata metadata = future.get();
//            System.out.printf("Message sent to topic %s with partition %d and offset %d%n",
//                    metadata.topic(), metadata.partition(), metadata.offset());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            producer.close();
//        }
    }
}