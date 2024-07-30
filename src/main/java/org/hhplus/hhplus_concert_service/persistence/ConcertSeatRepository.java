package org.hhplus.hhplus_concert_service.persistence;

import jakarta.persistence.LockModeType;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Integer> {

    //특정 콘서트 좌석 조회
    List<ConcertSeat> findAllByItemId(int itemId);

    //특정 좌석 조회
    @Lock(LockModeType.OPTIMISTIC)
    ConcertSeat findBySeatId(int seatId);


    //비관적 락 사용을 위한 특정 좌석 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ConcertSeat s WHERE s.seatId = :seatId")
    ConcertSeat lockSeatById(@Param("seatId") int seatId);
}
