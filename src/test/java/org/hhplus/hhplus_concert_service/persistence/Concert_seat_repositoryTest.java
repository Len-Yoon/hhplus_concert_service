package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Concert_seat_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Concert_seat_repositoryTest.class);
    @Autowired
    Concert_seat_repository concertSeatRepository;

    @Test
    @DisplayName("데이터 입력 테스트")
    void insertDataTest() {

        Concert_seat concertSeat = new Concert_seat();

        for(int i = 1; i <= 50; i++) {
            concertSeat.setItemId(1);
            concertSeat.setSeatNum(i);
            concertSeat.setSeatPrice(50000);
            concertSeat.setStatus("예약가능");

            concertSeatRepository.save(concertSeat);
        }

        for(int i = 1; i <= 50; i++) {
            concertSeat.setItemId(2);
            concertSeat.setSeatNum(i);
            concertSeat.setSeatPrice(50000);
            concertSeat.setStatus("예약가능");

            concertSeatRepository.save(concertSeat);
        }
    }

    @Test
    @DisplayName("특정 콘서트 좌석 정보 조회")
    void concertSeatTest() {
        List<Concert_seat> seatList = concertSeatRepository.findAllByItemId(1);
    }

    @Test
    @DisplayName("좌석 상태 변경")
    void updateStatusTest() {
        Concert_seat concertSeat = concertSeatRepository.findBySeatId(1);

        concertSeat.setStatus("예약완료");

        concertSeatRepository.save(concertSeat);
    }

}