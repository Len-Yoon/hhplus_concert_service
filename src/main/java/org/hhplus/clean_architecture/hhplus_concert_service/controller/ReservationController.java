package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    //예약 가능 날짜 조회 API
    @GetMapping("/checkDate")
    public void checkDate() {

    }

    //예약 가능 좌석 조회 API
    @GetMapping("/checkSeat")
    public void checkSeat() {

    }

    //임시 예약 저장 API
    @PostMapping("saveTemporaryReservation")
    public void saveTemporaryReservation() {

    }

    //예약 저장 API
    @PostMapping("saveReservation")
    public void saveReservation() {

    }
}
