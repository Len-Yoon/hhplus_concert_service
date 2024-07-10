package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface Reservation_repository extends JpaRepository<Reservation, Integer> {

    //유저 예약내역 조회
    List<Reservation> findAllByUserId(String userId);

    //특정 예약내역 조회
    Reservation findByReservationId(int reservationId);
}
