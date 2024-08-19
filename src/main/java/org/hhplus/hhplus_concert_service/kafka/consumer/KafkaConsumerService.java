package org.hhplus.hhplus_concert_service.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final BlockingQueue<String> messages = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "reservation-topic", groupId = "reservation-group")
    public void reservationListen(String message) {
        messages.offer(message);
        log.info("Received message: " + message);
    }

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void paymentListener(String message) {
        messages.offer(message);
        log.info("Received message: " + message);
    }

    @KafkaListener(topics = "deleteToken-topic", groupId = "deleteToken-group")
    public void deleteTokenListener(String message) {
        messages.offer(message);
        log.info("Received message: " + message);
    }

    public String getLastMessage() {
        return messages.poll();
    }
}
