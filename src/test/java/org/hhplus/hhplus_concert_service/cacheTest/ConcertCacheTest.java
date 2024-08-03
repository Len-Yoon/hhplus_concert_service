package org.hhplus.hhplus_concert_service.cacheTest;

import org.hhplus.hhplus_concert_service.business.service.ConcertService;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.persistence.ConcertItemRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConcertCacheTest {

    private static final Logger log = LoggerFactory.getLogger(ConcertCacheTest.class);
    @Autowired
    private ConcertService concertService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertItemRepository concertItemRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    private List<Concert> concerts;

//    @BeforeEach
//    public void setUp() {
//        Concert concert1 = new Concert();
//        Concert concert2 = new Concert();
//
//        concerts = Arrays.asList(concert1, concert2);
//
//        when(concertRepository.findByStatus("Y")).thenReturn(concerts);
//    }

    @Test
    @DisplayName("대량 데이터 입력")
    void SetUpData() {

        for(int i = 0; i < 1000; i++) {
            Concert concert = new Concert();
            concert.setStatus("Y");
            concert.setTitle("TEST"+i);
            concert.setCreatedAt(LocalDateTime.now());

            concertRepository.save(concert);

            for(int j = 0; j < 5; j++) {
                ConcertItem concertItem = new ConcertItem();
                concertItem.setConcertId(i+1);
                concertItem.setConcertDate(LocalDate.now().plusDays(1));

                concertItemRepository.save(concertItem);

                for(int k = 0; k < 50; k++) {
                    ConcertSeat concertSeat = new ConcertSeat();

                    concertSeat.setItemId(j+1);
                    concertSeat.setStatus("Y");
                    concertSeat.setSeatPrice(20000);
                    concertSeat.setSeatNum(k);

                    concertSeatRepository.save(concertSeat);
                }
            }
        }
    }

    @Test
    public void checkConcert_cacheTimingTest() {
        // Redis 캐시를 비우고 성능 측정
        cacheManager.getCache("concert_status_cache").clear(); // 캐시 비우기

        // 첫 번째 호출: 캐시가 비어 있는 상태에서의 성능 측정
        long startWithoutCache = System.nanoTime();
        List<Concert> result1 = concertService.checkConcert();
        long endWithoutCache = System.nanoTime();
        long timeWithoutCache = endWithoutCache - startWithoutCache;

        // 두 번째 호출: 첫 번째 호출 후 Redis 캐시가 채워진 상태에서의 성능 측정
        long startWithCache = System.nanoTime();
        List<Concert> result2 = concertService.checkConcert();
        long endWithCache = System.nanoTime();
        long timeWithCache = endWithCache - startWithCache;

        // 결과 확인
        assertThat(result1).isEqualTo(concerts);
        assertThat(result2).isEqualTo(concerts);
        log.info("Time without cache: " + timeWithoutCache + " ns");
        log.info("Time with cache: " + timeWithCache + " ns");

    }

    @Test
    public void checkConcertDate_cacheTimingTest() {
        // Redis 캐시를 비우고 성능 측정
        cacheManager.getCache("concert_date_cache").clear(); // 캐시 비우기

        // 첫 번째 호출: 캐시가 비어 있는 상태에서의 성능 측정
        long startWithoutCache = System.nanoTime();
        List<ConcertItem> result1 = concertService.checkConcertDate(1);
        long endWithoutCache = System.nanoTime();
        long timeWithoutCache = endWithoutCache - startWithoutCache;

        // 두 번째 호출: 첫 번째 호출 후 Redis 캐시가 채워진 상태에서의 성능 측정
        long startWithCache = System.nanoTime();
        List<ConcertItem> result2 = concertService.checkConcertDate(1);
        long endWithCache = System.nanoTime();
        long timeWithCache = endWithCache - startWithCache;

        // 결과 확인
        assertThat(result1).isEqualTo(concerts);
        assertThat(result2).isEqualTo(concerts);
        log.info("Time without cache: " + timeWithoutCache + " ns");
        log.info("Time with cache: " + timeWithCache + " ns");

    }

}
