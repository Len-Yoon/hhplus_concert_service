package org.hhplus.hhplus_concert_service.business.service;

import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;

import java.time.LocalDate;
import java.util.List;

public interface ConcertService {

    //콘서트 목록 조회
    List<Concert> checkConcert();

    //콘서트 예약 날짜 조회
    List<ConcertItem> checkConcertDate(int concertId);

    //콘서트 예약 좌석 조회
    List<ConcertSeat> checkConcertSeat(int concertItemId);

    //날짜 통한 예약 가능 콘서트 조회
    List<Concert> checkConcertByConcertDate(LocalDate checkDate);
}
