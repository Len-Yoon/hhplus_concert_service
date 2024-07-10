package org.hhplus.hhplus_concert_service.service;

import org.hhplus.hhplus_concert_service.entity.Reservation;

import java.util.List;

public interface ReservationService {

    //예약 요청
    void reservation(String userId, int concertId, int itemId, int seatId, int totalPrice, String status);

    //예약 완료
    void reservationCompleted(int reservationId, int paymentId);

    //특정 유저 예약 내역 조회
    List<Reservation> checkReservations(String userId);
}
