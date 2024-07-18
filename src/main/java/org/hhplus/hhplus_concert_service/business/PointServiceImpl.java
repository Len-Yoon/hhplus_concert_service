package org.hhplus.hhplus_concert_service.business;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.exception.AllExceptions;
import org.hhplus.hhplus_concert_service.persistence.Point_repository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueue_repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private static final Logger log = LoggerFactory.getLogger(PointServiceImpl.class);
    private final Point_repository pointRepository;
    private final TokenQueue_repository tokenQueueRepository;

    @Override
    public Point checkPoint(String userId) {
        return pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
    }

    @Override
    public void plusPoint(String userId, int chargePoint) {
        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);

        Point newPoint = new Point();

        newPoint.setUserId(userId);
        newPoint.setPoint(point.getPoint() + chargePoint);

        pointRepository.save(newPoint);

    }

    @Override
    public void minusPoint(String userId, int totalPrice) {
        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserId(userId);

        int holdPoint = point.getPoint();
        String status = tokenQueue.getStatus();
        if(!status.equals("진행")) {
            throw new RuntimeException();
        } else {
            if(holdPoint < totalPrice) {
                throw  new RuntimeException();
            } else {
                Point newPoint = new Point();
                newPoint.setUserId(userId);
                newPoint.setPoint(point.getPoint() - totalPrice);

                pointRepository.save(newPoint);
            }
        }
    }
}
