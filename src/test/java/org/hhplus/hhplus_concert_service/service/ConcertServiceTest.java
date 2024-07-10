package org.hhplus.hhplus_concert_service.service;

import org.hhplus.hhplus_concert_service.entity.Concert;
import org.hhplus.hhplus_concert_service.entity.Concert_item;
import org.hhplus.hhplus_concert_service.entity.Concert_seat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConcertServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ConcertServiceTest.class);

    @Autowired
    ConcertService concertService;

    @Test
    @DisplayName("콘서트 조회 테스트")
    void checkConcert() {
        List<Concert> concertList = concertService.checkConcert();
    }

    @Test
    void checkConcertDate() {
        List<Concert_item> concertItemList = concertService.checkConcertDate(1);
    }

    @Test
    void checkConcertSeat() {
        List<Concert_seat> concertSeatList = concertService.checkConcertSeat(1);
    }

    @Test
    void checkConcertByConcertDate() {
        LocalDate dummyDate = LocalDate.parse("2024-07-09");

        List<Concert> concertList = concertService.checkConcertByConcertDate(dummyDate);
    }
}