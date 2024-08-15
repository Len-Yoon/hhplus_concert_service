package org.hhplus.hhplus_concert_service.business.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhplus.hhplus_concert_service.business.service.PointService;

import org.hhplus.hhplus_concert_service.business.service.event.payment.OnPaymentEvent;
import org.hhplus.hhplus_concert_service.domain.OutboxEvent;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.OutBoxEventRepository;
import org.hhplus.hhplus_concert_service.persistence.PointRepository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentPointFacade {

    private final PointService pointService;
    private final PointRepository pointRepository;
    private final TokenQueueRepository tokenQueueRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OutBoxEventRepository outBoxEventRepository;

    public void processPointPayment(String userId,int totalPrice, int concertId, int reservationId) {

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserIdAndConcertId(userId, concertId);

        int holdPoint = point.getPoint();
        String token = tokenQueue.getToken();

        try {
            if(token.isEmpty()) {
                throw new RuntimeException();
            } else {
                if(holdPoint < totalPrice) {
                    throw  new RuntimeException("Insufficient points");
                } else {
                    pointService.minusPoint(userId, totalPrice, concertId, reservationId);

                    String eventType = "PAYMENT_EVENT";
                    String payload = String.format("{\"totalPrice\": %d, \"reservationId\": %d}", totalPrice, reservationId);

                    OutboxEvent outboxEvent = new OutboxEvent(eventType, payload);
                    outBoxEventRepository.save(outboxEvent);

                    eventPublisher.publishEvent(new OnPaymentEvent(this, totalPrice, reservationId));
                }
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("Exception occurred while updating point", e);
            throw new RuntimeException(e);

        }
    }
}
