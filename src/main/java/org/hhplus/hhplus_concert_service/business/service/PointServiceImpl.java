package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.event.point.PointMinusEvent;
import org.hhplus.hhplus_concert_service.business.service.event.point.PointPlusEvent;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.PointRepository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

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

    @Override
    public void plusPoint(String userId, int chargePoint) {

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);

        try {
//            Point newPoint = new Point();
//
//            newPoint.setUserId(userId);
//            newPoint.setPoint(point.getPoint() + chargePoint);
//
//            pointRepository.save(newPoint);

            eventPublisher.publishEvent(new PointPlusEvent(this, userId, chargePoint));
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }

    //낙관적 락 적용
    @Override
    public void minusPoint(String userId, int totalPrice, int concertId) {

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserIdAndConcertId(userId, concertId);

        int holdPoint = point.getPoint();
        String token = tokenQueue.getToken();

        try {
            if(token.isEmpty()) {
                throw new RuntimeException();
            } else {
                if(holdPoint < totalPrice) {
                    throw  new RuntimeException();
                } else {
//                    Point newPoint = new Point();
//                    newPoint.setUserId(userId);
//                    newPoint.setPoint(point.getPoint() - totalPrice);

//                    pointRepository.save(newPoint);
//                    tokenQueueRepository.deleteByUserIdAndConcertId(userId, concertId);

                    //이벤트 발행
                    eventPublisher.publishEvent(new PointMinusEvent(this, userId, totalPrice, concertId));
                }
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }
}
