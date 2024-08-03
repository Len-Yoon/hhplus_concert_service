//package org.hhplus.hhplus_concert_service.lockTest;
//
//import jakarta.transaction.Transactional;
//import org.hhplus.hhplus_concert_service.business.service.PointService;
//import org.hhplus.hhplus_concert_service.domain.Point;
//import org.hhplus.hhplus_concert_service.domain.TokenQueue;
//import org.hhplus.hhplus_concert_service.persistence.PointRepository;
//import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.orm.ObjectOptimisticLockingFailureException;
//import org.springframework.test.annotation.Rollback;
//
//import static org.assertj.core.api.Fail.fail;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@Transactional
//public class PointLockTest {
//
//    @Autowired
//    private PointRepository pointRepository;
//
//    @Autowired
//    private TokenQueueRepository tokenQueueRepository;
//
//    @Autowired
//    private PointService pointService;
//
//    @BeforeEach
//    void setUp() {
//
//        Point point = new Point();
//        point.setUserId("testUser");
//        point.setPoint(200);
//
//        TokenQueue tokenQueue = new TokenQueue();
//        tokenQueue.setUserId("testUser");
//        tokenQueue.setStatus("P");
//
//        pointRepository.save(point);
//        tokenQueueRepository.save(tokenQueue);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("낙관적 락 테스트")
//    void testPlusPoint_optimisticLocking() {
//        String userId = "testUser";
//        int totalPrice = 100;
//
//        Runnable task = () -> {
//            try {
//                pointService.plusPoint(userId, totalPrice);
//            } catch (ObjectOptimisticLockingFailureException e) {
//                throw new RuntimeException();
//            }
//        };
//
//        Thread thread1 = new Thread(task);
//        Thread thread2 = new Thread(task);
//        Thread thread3 = new Thread(task);
//        Thread thread4 = new Thread(task);
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//
//        try {
//            thread1.join();
//            thread2.join();
//            thread3.join();
//            thread4.join();
//        } catch (InterruptedException e) {
//            fail("Thread interrupted");
//        }
//
//        Point updatedPoint = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
//        assertNotNull(updatedPoint);
//        assertTrue(updatedPoint.getPoint() >= 0);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("낙관적 락 테스트")
//    void testMinusPoint_optimisticLocking() {
//        String userId = "testUser";
//        int totalPrice = 100;
//
//        Runnable task = () -> {
//            try {
//                pointService.minusPoint(userId, totalPrice);
//            } catch (ObjectOptimisticLockingFailureException e) {
//                throw new RuntimeException();
//            }
//        };
//
//        Thread thread1 = new Thread(task);
//        Thread thread2 = new Thread(task);
//        Thread thread3 = new Thread(task);
//        Thread thread4 = new Thread(task);
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//
//        try {
//            thread1.join();
//            thread2.join();
//            thread3.join();
//            thread4.join();
//        } catch (InterruptedException e) {
//            fail("Thread interrupted");
//        }
//
//        Point updatedPoint = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
//        assertNotNull(updatedPoint);
//        assertTrue(updatedPoint.getPoint() >= 0);
//    }
//}
