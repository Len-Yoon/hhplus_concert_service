package org.hhplus.hhplus_concert_service.transactionTest;

import org.aspectj.lang.annotation.Before;
import org.hhplus.hhplus_concert_service.business.service.PointService;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.persistence.PointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PointServiceKafkaTest {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointService pointService;

    @Test
    public void setUp() {
        Point point = new Point();

        point.setPoint(200);
        point.setUserId("user1");

        pointRepository.save(point);
    }

    @Test
    public void testPlusPointTransaction() {
        String userId = "user1";

        // 포인트 충전 테스트
        pointService.plusPoint(userId, 100);
        Point point = pointService.checkPoint(userId);
        assertEquals(100, point.getPoint());

        // 포인트 충전 중 예외 발생 시 트랜잭션 롤백 확인
        assertThrows(TransactionSystemException.class, () -> {
            pointService.plusPoint(userId, -100); // 잘못된 입력으로 인해 예외 발생 기대
        });

        // 롤백 후 포인트가 원상복구 되었는지 확인
        Point pointAfterRollback = pointService.checkPoint(userId);
        assertEquals(100, pointAfterRollback.getPoint());
    }
}
