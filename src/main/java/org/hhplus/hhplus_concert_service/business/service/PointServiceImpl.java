package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.event.payment.OnPaymentEvent;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.persistence.PointRepository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private static final Logger log = LoggerFactory.getLogger(PointServiceImpl.class);

    private final PointRepository pointRepository;
    private final TokenQueueRepository tokenQueueRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Point checkPoint(String userId) {
        return pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
    }

    @Transactional
    @Override
    public void plusPoint(String userId, int chargePoint) {

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

    //낙관적 락 적용
    @Transactional
    @Override
    public void minusPoint(String userId, int totalPrice, int concertId, int reservationId) {

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);

        try {
            Point newPoint = new Point();
            newPoint.setUserId(userId);
            newPoint.setPoint(point.getPoint() - totalPrice);

            pointRepository.save(newPoint);

            eventPublisher.publishEvent(new OnPaymentEvent(this, totalPrice, reservationId));
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }
}
