package org.hhplus.hhplus_concert_service.service;

import org.hhplus.hhplus_concert_service.entity.Concert;
import org.hhplus.hhplus_concert_service.entity.Concert_item;
import org.hhplus.hhplus_concert_service.entity.Concert_seat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConcertService {

    //콘서트 목록 조회
    List<Concert> checkConcert();

    //콘서트 예약 날짜 조회
    List<Concert_item> checkConcertDate(int concertId);

    //콘서트 예약 좌석 조회
    List<Concert_seat> checkConcertSeat(int concertItemId);

    //날짜 통한 예약 가능 콘서트 조회
    List<Concert> checkConcertByConcertDate(LocalDate checkDate);
}
