package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Reservation_repository extends JpaRepository<Reservation, Integer> {

    //유저 예약내역 조회
    List<Reservation> findAllByUserId(String userId);

    //특정 예약내역 조회
    Reservation findByReservationId(int reservationId);

    //임시예약 상태 내역 조회
    List<Reservation> findAllByStatus(String status);
}
