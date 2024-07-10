package org.hhplus.hhplus_concert_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Test
    void reservation() {
        String userId = "유저1";
        int concertId = 1;
        int itemId = 1;
        int seatId = 1;
        int totalPrice = 50000;
        String status = "임시예약";

        reservationService.reservation(userId, concertId, itemId, seatId, totalPrice, status);
    }

    @Test
    void reservationCompleted() {
        int reservationId = 1;
        int paymentId = 1;

        reservationService.reservationCompleted(reservationId, paymentId);
    }

    @Test
    void checkReservations() {
        String userId = "유저1";

        reservationService.checkReservations(userId);
    }
}