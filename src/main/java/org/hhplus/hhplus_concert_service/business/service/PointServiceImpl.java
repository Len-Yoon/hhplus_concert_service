package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.constans.TokenConstants;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.Point_repository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueue_repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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

    //낙관적 락 적용
    @Override
    public void minusPoint(String userId, int totalPrice) {
        int retryCount = 5;

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserId(userId);

        int holdPoint = point.getPoint();
        String status = tokenQueue.getStatus();

        while (retryCount > 0) {
            try {
                if(!TokenConstants.STATUS_IN_PROGRESS.equals(status)) {
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
            } catch (ObjectOptimisticLockingFailureException e) {
                retryCount --;
                if(retryCount == 0) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
