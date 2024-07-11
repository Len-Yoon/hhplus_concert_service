package org.hhplus.hhplus_concert_service.service;

import org.hhplus.hhplus_concert_service.entity.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceTest {

    @Autowired
    PointService pointService;

    @Test
    @DisplayName("유저 포인트 조회")
    void checkPoint() {
        String userId = "유저1";

        pointService.checkPoint(userId);
    }

    @Test
    void plusPoint() {
        String userId = "유저1";
        int chargePoint = 50000;

        pointService.plusPoint(userId, chargePoint);
    }

    @Test
    void minusPoint() {
        String userId = "유저1";
        int totalPrice = 10000;

        pointService.minusPoint(userId, totalPrice);
    }
}