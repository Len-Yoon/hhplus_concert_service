package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Concert_seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Concert_seat_repository extends JpaRepository<Concert_seat, Integer> {

    //특정 콘서트 좌석 조회
    List<Concert_seat> findAllByItemId(int itemId);

    //특정 좌석 조회(상태변경용)
    Concert_seat findBySeatId(int seatId);
}
