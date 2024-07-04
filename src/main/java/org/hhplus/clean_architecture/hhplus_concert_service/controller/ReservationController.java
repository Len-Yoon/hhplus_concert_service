package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.clean_architecture.hhplus_concert_service.entity.Concert;
import org.hhplus.clean_architecture.hhplus_concert_service.entity.ConcertSeat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    //예약 가능 날짜 조회 API
    @GetMapping("/checkDate")
    public Concert checkDate(HttpServletRequest request, HttpServletResponse response) {
        int concertId = Integer.parseInt(request.getParameter("concertId"));

        String title = "테스트";
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = LocalDateTime.now();
        LocalDateTime reserveStartAt = LocalDateTime.now();
        LocalDateTime reserveEndAt = LocalDateTime.now();

        return new Concert(concertId,title,startAt,endAt,reserveStartAt,reserveEndAt,LocalDateTime.now());
    }

    //예약 가능 좌석 조회 API
    @GetMapping("/checkSeat")
    public List<ConcertSeat> checkSeat(HttpServletRequest request, HttpServletResponse response) {
        int concertId = Integer.parseInt(request.getParameter("concertId"));

        int concertSeatId = 1;
        int price = 2000;
        LocalDateTime perpormanceAt = LocalDateTime.now();
        String status = "예약가능";

        List<ConcertSeat> rtn = new ArrayList<>();
        for(int i = 1; i <= 50; i++) {
            rtn.add(new ConcertSeat(concertSeatId,concertId,i,price,perpormanceAt,status, LocalDateTime.now()));
        }

        return rtn;
    }

    //임시 예약 저장 API
    @PostMapping("saveTemporaryReservation")
    public int saveTemporaryReservation(HttpServletRequest request, HttpServletResponse response) {
        int concertSeatId = Integer.parseInt(request.getParameter("concertSeatId"));
        String userId = request.getParameter("userId");
        int temporaryReservationId = 1;

        return temporaryReservationId;
    }

    //예약 저장 API
    @PostMapping("saveReservation")
    public int saveReservation(HttpServletRequest request, HttpServletResponse response) {
        int concertSeatId = Integer.parseInt(request.getParameter("concertSeatId"));
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        String userId = request.getParameter("userId");

        int reservationId = 1;

        return reservationId;
    }
}
