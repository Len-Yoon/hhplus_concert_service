package org.hhplus.hhplus_concert_service.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.entity.Point;
import org.hhplus.hhplus_concert_service.entity.TokenQueue;
import org.hhplus.hhplus_concert_service.repository.Point_repository;
import org.hhplus.hhplus_concert_service.repository.TokenQueue_repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

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
        newPoint.setPoint(point.getPoint()+chargePoint);

        pointRepository.save(newPoint);

    }

    @Override
    public void minusPoint(String userId, int totalPrice) {
        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserId(userId);

        int holdPoint = point.getPoint();
        String status = tokenQueue.getStatus();
        if(!status.equals("진행")) {
            throw new RuntimeException("오류가 발생했습니다.");
        } else {
            if(holdPoint < totalPrice) {
                throw  new RuntimeException("포인트가 부족합니다.");
            } else {
                Point newPoint = new Point();
                newPoint.setUserId(userId);
                newPoint.setPoint(point.getPoint()-totalPrice);

                pointRepository.save(newPoint);
            }
        }
    }
}
