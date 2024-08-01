package org.hhplus.hhplus_concert_service.business.service;

import org.hhplus.hhplus_concert_service.domain.Point;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface PointService {

    //유저 포인트 조회
    @Cacheable(value = "check_point_cache", cacheManager = "contentCacheManager")
    Point checkPoint(String userId);

    //포인트 충전
    @CacheEvict(value = "check_point_cache", allEntries = true)
    void plusPoint(String userId, int chargePoint);

    //포인트 차감
    @CacheEvict(value = "check_point_cache", allEntries = true)
    void minusPoint(String userId, int totalPrice, int concertId);
}
