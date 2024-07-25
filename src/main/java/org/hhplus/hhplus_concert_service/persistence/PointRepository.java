package org.hhplus.hhplus_concert_service.persistence;

import jakarta.persistence.LockModeType;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;


public interface PointRepository extends JpaRepository<Point, Integer> {

    //유저 포인트 조회
    @Lock(LockModeType.OPTIMISTIC)
    Point findFirstByUserIdOrderByPointIdDesc(String userId);


}
