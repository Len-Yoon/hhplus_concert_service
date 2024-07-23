package org.hhplus.hhplus_concert_service.lockTest;

import jakarta.transaction.Transactional;
import org.hhplus.hhplus_concert_service.business.service.PointService;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.Point_repository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueue_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PointLockTest {

    @Autowired
    private Point_repository pointRepository;

    @Autowired
    private TokenQueue_repository tokenQueueRepository;

    private PointService pointService;

    @BeforeEach
    void setUp() {

        Point point = new Point();
        point.setUserId("testUser");
        point.setPoint(200);

        TokenQueue tokenQueue = new TokenQueue();
        tokenQueue.setUserId("testUser");
        tokenQueue.setStatus("진행");

        pointRepository.save(point);
        tokenQueueRepository.save(tokenQueue);
    }

    @Test
    @Transactional
    @Rollback
    void testMinusPoint_optimisticLocking() {
        String userId = "testUser";
        int totalPrice = 100;

        Runnable task = () -> {
            try {
                pointService.minusPoint(userId, totalPrice);
            } catch (Exception e) {
                // handle exception
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            fail("Thread interrupted");
        }

        Point updatedPoint = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        assertNotNull(updatedPoint);
        assertTrue(updatedPoint.getPoint() >= 0);
    }

}
