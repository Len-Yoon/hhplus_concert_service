package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.Utils;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.business.PointService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    //포인트 조회
    @GetMapping("")
    public Point checkPoint(HttpServletRequest request, HttpServletResponse response) {
        String userId = Utils.checkNull(request.getParameter("userId"));

        return pointService.checkPoint(userId);
    }

    //포인트 충전
    @PostMapping("plusPoint")
    public void plusPoint(HttpServletRequest request, HttpServletResponse response) {
        String userId = Utils.checkNull(request.getParameter("userId"));
        int chargePrice = Utils.checkNullByInt(request.getParameter("chargePoint"));

        pointService.plusPoint(userId, chargePrice);
    }

    //포인트 차감
    @PostMapping("minusPoint")
    public void minusPoint(HttpServletRequest request, HttpServletResponse response) {
       String userId = Utils.checkNull(request.getParameter("userId"));
       int totalPrice = Utils.checkNullByInt(request.getParameter("totalPoint"));

       pointService.minusPoint(userId, totalPrice);
    }

}
