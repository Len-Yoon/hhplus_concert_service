package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Reservation_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Reservation_repositoryTest.class);

    @Autowired
    Reservation_repository reservationRepository;

    @Test
    @DisplayName("데이터 입력 테스트")
    void insertDataTest() {

        Reservation reservation  = new Reservation();

        reservation.setUserId("유저1");
        reservation.setConcertId(1);
        reservation.setSeatId(1);
        reservation.setItemId(1);
        reservation.setPayment_id(1);
        reservation.setTotalPrice(50000);
        reservation.setStatus("임시예약");

        reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("유저 예약 내역 조회")
    void UserReservationTest() {
        List<Reservation> reservationList = reservationRepository.findAllByUserId("유저1");
    }

    @Test
    @DisplayName("예약 상태 변경")
    void updateStatusTest() {
        Reservation reservation = reservationRepository.findByReservationId(1);

        reservation.setStatus("예약완료");

        reservationRepository.save(reservation);
    }
}