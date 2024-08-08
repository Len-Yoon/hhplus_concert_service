package org.hhplus.hhplus_concert_service.business.service.listener.point;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.event.point.PointPlusEvent;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.persistence.PointRepository;
import org.springframework.context.event.EventListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointPlusListener {

    private final PointRepository pointRepository;

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
