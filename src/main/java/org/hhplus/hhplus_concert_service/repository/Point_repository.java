package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Point_repository extends JpaRepository<Point, Integer> {

    //유저 포인트 조회
    Point findFirstByUserIdOrderByPointIdDesc(String userId);

}
