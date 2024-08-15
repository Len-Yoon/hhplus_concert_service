package org.hhplus.hhplus_concert_service.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String RESERVATION_TOPIC = "reservation_topic";
    private final String PAYMENT_TOPIC = "payment-topic";
    private final String DELETETOKEN_TOPIC = "deleteToken-topic";
    private final String CONCERT_TOPIC = "concert-topic";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendReservationMessage(String topic, String reservationMessage) {
        kafkaTemplate.send(RESERVATION_TOPIC, reservationMessage);
        log.info("Sent message to " + RESERVATION_TOPIC + ": " + reservationMessage);
    }
}
