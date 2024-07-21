package org.hhplus.hhplus_concert_service.business.service;

import org.hhplus.hhplus_concert_service.domain.Point;

public interface PointService {

    //유저 포인트 조회
    Point checkPoint(String userId);

    //포인트 충전
    void plusPoint(String userId, int chargePoint);

    //포인트 차감
    void minusPoint(String userId, int totalPrice);
}
