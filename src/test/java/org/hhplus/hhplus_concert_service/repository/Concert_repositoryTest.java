package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Concert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Concert_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Concert_repositoryTest.class);

    @Autowired
    Concert_repository concertRepository;

    @Test
    @DisplayName("데이터 입력 테스트")
    void insertDataTest() {
        Concert concert = new Concert();

        concert.setConcertId(1);
        concert.setTitle("싸이 흠뻑쇼");
        concert.setStatus("Y");
        concert.setCreatedAt(LocalDateTime.now());

        concertRepository.save(concert);
    }

    @Test
    @DisplayName("콘서트 전체 조회 테스트")
    void concertTest() {
        List<Concert> concertList = concertRepository.findAll();

    }

    @Test
    @DisplayName("예약 가능 콘서트 조회")
    void StatusTest() {
        List<Concert> concertList = concertRepository.findByStatus("Y");

    }

    @Test
    @DisplayName("콘서트 정보 변경 테스트")
    void updateDataTest() {
        Concert concert = concertRepository.findByConcertId(1);

        concert.setTitle("아이유 콘서트");

        concertRepository.save(concert);
    }

}