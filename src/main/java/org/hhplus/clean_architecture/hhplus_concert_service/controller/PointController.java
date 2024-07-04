package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.clean_architecture.hhplus_concert_service.dto.point.ChargePoint;
import org.hhplus.clean_architecture.hhplus_concert_service.dto.point.DeductedPoint;
import org.hhplus.clean_architecture.hhplus_concert_service.entity.Point;
import org.hhplus.clean_architecture.hhplus_concert_service.entity.PointHistory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {

    //포인트 조회 API
    @GetMapping("/checkPoint")
    public Point checkPoint(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        int point = 2000;
        int pointid = 1;

        return new Point(pointid, userId, point);
    }

    //포인트 충전 API
    @PostMapping("chargePoint")
    public ChargePoint chargePoint(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        int amount = 2000;

        return new ChargePoint(userId,amount);
    }

    //포인트 차감 API
    @PostMapping("deductedPoint")
    public DeductedPoint deductedPoint(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        int amount = 2000;

        return new DeductedPoint(userId, amount);
    }

    //포인트 사용 내역 조회 API
    @GetMapping("checkPointHistory")
    public PointHistory checkPointHistory(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");

        int pointHistoryId = 1;
        int pointId = 1;
        int paymentId = 1;
        LocalDateTime createAt = LocalDateTime.now();

        return new PointHistory(pointHistoryId,pointId,userId,paymentId,createAt);
    }
}
