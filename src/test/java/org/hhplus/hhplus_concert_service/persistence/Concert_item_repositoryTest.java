package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Concert_item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Concert_item_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Concert_item_repositoryTest.class);

    @Autowired
    Concert_item_repository concertItemRepository;

    @Test
    @DisplayName("데이터 입력 테스트")
    void insertDataTest() {

        Concert_item concertItem = new Concert_item();

        concertItem.setConcertId(1);
        concertItem.setConcertDate(LocalDateTime.now());

        concertItemRepository.save(concertItem);
    }

    @Test
    @DisplayName("콘서트 상세 정보 전체 조회 테스트")
    void concertItemTest() {
        List<Concert_item> concerItemtList = concertItemRepository.findAll();
    }

    @Test
    @DisplayName("날짜 통한 콘서트 조회")
    void checkConcertDateTest() {
        String thisDate = LocalDate.parse("2024-07-09").toString();

        List<Concert_item> concertItemList = concertItemRepository.findAll();
        List<Concert_item> newConcertItemList = new ArrayList<>();

        for(int i = 0; i < concertItemList.size(); i++){
            String date = String.valueOf(concertItemList.get(i).getConcertDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if (date.equals(thisDate)){
                newConcertItemList.add(concertItemList.get(i));
            }
        }

    }

    @Test
    @DisplayName("데이터 변경 테스트")
    void updateDataTest() {
        Concert_item concertItem = concertItemRepository.findByItemId(1);

        concertItem.setConcertDate(LocalDateTime.now());

        concertItemRepository.save(concertItem);
    }
}