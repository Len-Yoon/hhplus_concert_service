package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Point_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Point_repositoryTest.class);

    @Autowired
    Point_repository pointRepository;

    @Test
    @DisplayName("데이트 입력 테스트")
    void insertDataTest() {

        Point point = new Point();

        point.setPointId(1);
        point.setUserId("유저1");
        point.setPoint(150000);

        pointRepository.save(point);
    }

    @Test
    @DisplayName("유저 포인트 조회")
    void checkPointTest() {
        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc("유저1");
    }

    @Test
    @DisplayName("포인트 차감 테스트")
    void minusPointTest() {
        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc("유저1");
        Point newPoint = new Point();

        newPoint.setUserId(point.getUserId());
        newPoint.setPoint(point.getPoint()-50000);

        pointRepository.save(newPoint);
    }
}