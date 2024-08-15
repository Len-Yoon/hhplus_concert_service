package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.business.service.ReservationService;
import org.hhplus.hhplus_concert_service.interfaces.controller.dto.ReservationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    //예약 요청
    @PostMapping("")
    public void reservation(@Valid @ModelAttribute ReservationDTO reservationDTO) {
        String userId = reservationDTO.getUserId();
        int concertId = reservationDTO.getConcertId();
        int itemId = reservationDTO.getItemId();
        int seatId = reservationDTO.getSeatId();
        int totalPrice = reservationDTO.getTotalPrice();

        reservationService.reservation(userId, concertId, itemId, seatId, totalPrice, "임시예약");
    }

    //예약 완료
    @PostMapping("reservationCompleted")
    public void reservationCompleted(@Valid @ModelAttribute ReservationDTO reservationDTO) {

        String userId = reservationDTO.getUserId();
        int concertId = reservationDTO.getConcertId();
        int reservationId = reservationDTO.getReservationId();
        int paymentId = reservationDTO.getPaymentId();

        reservationService.reservationCompleted(userId, concertId, reservationId, paymentId);
    }

    //예약 내역 체크
    @GetMapping("checkReservation")
    public List<Reservation> checkReservation(@Valid @ModelAttribute ReservationDTO reservationDTO) {
        String userId = reservationDTO.getUserId();

        return reservationService.checkReservations(userId);
    }

}
