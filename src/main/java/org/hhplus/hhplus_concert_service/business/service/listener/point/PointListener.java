package org.hhplus.hhplus_concert_service.business.service.listener.point;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.event.point.PointMinusEvent;
import org.hhplus.hhplus_concert_service.business.service.event.point.PointPlusEvent;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.PointRepository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.springframework.context.event.EventListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointListener {

    private final PointRepository pointRepository;
    private final TokenQueueRepository tokenQueueRepository;

    @Async
    @EventListener
    public void handlePointMinus(PointMinusEvent event) {
        String userId = event.getUserId();
        int totalPrice = event.getTotalPrice();
        int concertId = event.getConcertId();

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserIdAndConcertId(userId, concertId);

        int holdPoint = point.getPoint();
        String token = tokenQueue.getToken();

        try {
            if (token.isEmpty()) {
                throw new RuntimeException("Token is empty");
            } else if (holdPoint < totalPrice) {
                throw new RuntimeException("Insufficient points");
            } else {
                Point newPoint = new Point();
                newPoint.setUserId(userId);
                newPoint.setPoint(point.getPoint() - totalPrice);

                pointRepository.save(newPoint);
                tokenQueueRepository.deleteByUserIdAndConcertId(userId, concertId);
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }

    @Async
    @EventListener
    public void HandlePointPlusEvent(PointPlusEvent event) {
        String userId = event.getUserId();
        int chargePoint = event.getChargePoint();

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);

        try {
            Point newPoint = new Point();

            newPoint.setUserId(userId);
            newPoint.setPoint(point.getPoint() + chargePoint);

            pointRepository.save(newPoint);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }
}
