package org.hhplus.hhplus_concert_service.persistence;

import jakarta.persistence.LockModeType;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface Concert_seat_repository extends JpaRepository<Concert_seat, Integer> {

    //특정 콘서트 좌석 조회
    List<Concert_seat> findAllByItemId(int itemId);

    //특정 좌석 조회(상태변경용)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Concert_seat findBySeatId(int seatId);
}
