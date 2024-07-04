package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {

    //포인트 조회 API
    @GetMapping("/checkPoint")
    public void checkPoint() {

    }

    //포인트 충전 API
    @PostMapping("chargePoint")
    public void chargePoint() {

    }

    //포인트 차감 API
    @PostMapping("deductedPoint")
    public void deductedPoint() {

    }

    //포인트 사용 내역 조회 API
    @GetMapping("checkPointHistory")
    public void checkPointHistory() {

    }
}
