package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.Utils;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.business.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    //예약 요청
    @PostMapping("")
    public void reservation(HttpServletRequest request, HttpServletResponse response) {
        int concertId = Utils.checkNullByInt(request.getParameter("concertId"));
        int itemId = Utils.checkNullByInt(request.getParameter("itemId"));
        int seatId = Utils.checkNullByInt(request.getParameter("seatId"));
        String userId = Utils.checkNull(request.getParameter("userId"));
        int totalPrice = Utils.checkNullByInt(request.getParameter("totalPrice"));

        reservationService.reservation(userId, concertId, itemId, seatId, totalPrice, "임시예약");
    }

    //예약 완료
    @PostMapping("reservationCompleted")
    public void reservationCompleted(HttpServletRequest request, HttpServletResponse response) {
        int reservationId = Utils.checkNullByInt(request.getParameter("reservationId"));
        int paymentId = Utils.checkNullByInt(request.getParameter("paymentId"));

        reservationService.reservationCompleted(reservationId, paymentId);
    }

    //예약 내역 체크
    @GetMapping("checkReservation")
    public List<Reservation> checkReservation(HttpServletRequest request, HttpServletResponse response) {
        String userId = Utils.checkNull(request.getParameter("userId"));

        return reservationService.checkReservations(userId);
    }

}
