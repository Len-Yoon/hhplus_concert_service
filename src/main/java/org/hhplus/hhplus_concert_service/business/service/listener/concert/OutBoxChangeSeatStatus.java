package org.hhplus.hhplus_concert_service.business.service.listener.concert;

import lombok.extern.slf4j.Slf4j;
import org.hhplus.hhplus_concert_service.domain.OutboxEvent;
import org.hhplus.hhplus_concert_service.persistence.OutBoxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OutBoxChangeSeatStatus {

    private final OutBoxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String CHANGE_SEAT_STATUS_TOPIC = "changeSeatStatus-topic";

    public OutBoxChangeSeatStatus(OutBoxEventRepository outboxEventRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000)  // 5초마다 실행
    public void publishOutboxEvents() {
        List<OutboxEvent> events = outboxEventRepository.findByProcessedFalse();

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send(CHANGE_SEAT_STATUS_TOPIC, event.getPayload()).get();

                // 메시지 전송이 성공하면 처리 완료로 표시
                event.setProcessed(true);
                outboxEventRepository.save(event);

            } catch (Exception e) {
                // 메시지 전송 실패 시 로그 기록, 이후에 다시 시도
                log.error("Failed to send event to Kafka, will retry: {}", event, e);
            }
        }
    }
}
