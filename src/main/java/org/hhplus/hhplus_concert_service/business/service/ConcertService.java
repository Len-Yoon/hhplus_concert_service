package org.hhplus.hhplus_concert_service.business.service;

import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConcertService {

    //콘서트 목록 조회
    @Cacheable(value = "concert_status_cache", cacheManager = "contentCacheManager")
    List<Concert> checkConcert();

    //콘서트 예약 날짜 조회
    @Cacheable(value = "concert_date_cache", cacheManager = "contentCacheManager")
    List<ConcertItem> checkConcertDate(int concertId);

    //콘서트 예약 좌석 조회
    List<ConcertSeat> checkConcertSeat(int concertItemId);

    //날짜 통한 예약 가능 콘서트 조회
    List<Concert> checkConcertByConcertDate(LocalDate checkDate);

    //콘서트 입력
    @CacheEvict(value = "concert_status_cache", allEntries = true)
    void concertInsert(String status,String title,LocalDate startDate,LocalDate endDate,int seatSize,String seatPrice);

//    //콘서트 Item 입력
//    @CacheEvict(value = "concert_date_cache", allEntries = true)
//    void concertItemInsert(int concertId, LocalDate startDate, LocalDate endDate);
//
//    //콘서트 Seat 입력
//    void concertSeatInsert(int itemId, int seatSize, String seatPrice, String status);

    //콘서트 상태변경
    void concertStatusChange(int seatId);
}
