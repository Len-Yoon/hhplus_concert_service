package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.business.service.PointService;
import org.hhplus.hhplus_concert_service.interfaces.controller.dto.PointDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
@Valid
public class PointController {

    private final PointService pointService;

    //포인트 조회
    @GetMapping("")
    public Point checkPoint(@Valid @ModelAttribute PointDTO pointDTO) {
        String userId = pointDTO.getUserId();

        return pointService.checkPoint(userId);
    }

    //포인트 충전
    @PostMapping("plusPoint")
    public void plusPoint(@Valid @ModelAttribute PointDTO pointDTO) {
        String userId = pointDTO.getUserId();
        int chargePrice = pointDTO.getChargePoint();

        pointService.plusPoint(userId, chargePrice);
    }

    //포인트 차감
    @PostMapping("minusPoint")
    public void minusPoint(@Valid @ModelAttribute PointDTO pointDTO) {
       String userId = pointDTO.getUserId();
       int totalPrice = pointDTO.getTotalPoint();

       pointService.minusPoint(userId, totalPrice);
    }

}
