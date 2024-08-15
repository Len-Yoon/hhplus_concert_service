package org.hhplus.hhplus_concert_service.business.service.listener.reservation;

import lombok.extern.slf4j.Slf4j;
import org.hhplus.hhplus_concert_service.domain.OutboxEvent;
import org.hhplus.hhplus_concert_service.persistence.OutBoxEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OutBoxResrevationCompletedPublisher {

    @Autowired
    private OutBoxEventRepository outboxEventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String CHANGE_SEAT_STATUS_TOPIC = "changeSeatStatus-topic";

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    @Transactional
    public void publishOutboxEvents() {
        List<OutboxEvent> events = outboxEventRepository.findByProcessedFalse();

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send(CHANGE_SEAT_STATUS_TOPIC, event.getPayload()).get();

                // 메시지 전송이 성공하면 아웃박스 이벤트를 처리 완료로 표시
                event.setProcessed(true);
                outboxEventRepository.save(event);
            } catch (Exception e) {
                // 전송 실패 시 로그를 남기고, 이후에 다시 시도
                log.error("Failed to send event to Kafka, will retry: {}", event, e);
            }
        }
    }
}
