package org.hhplus.hhplus_concert_service.unitTest;

import org.hhplus.hhplus_concert_service.business.ConcertServiceImpl;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.Concert_item;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.persistence.Concert_item_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_seat_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConcertServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ConcertServiceTest.class);

    @Mock
    private Concert_repository concertRepository;

    @Mock
    private Concert_item_repository concertItemRepository;

    @Mock
    private Concert_seat_repository concertSeatRepository;

    @InjectMocks
    private ConcertServiceImpl concertService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("콘서트 조회 테스트")
    void checkConcert() {
        List<Concert> mockConcerts = new ArrayList<>();
        mockConcerts.add(new Concert());

        when(concertRepository.findByStatus("Y")).thenReturn(mockConcerts);

        List<Concert> concerts = concertService.checkConcert();

        assertEquals(mockConcerts.size(), concerts.size());
    }

    @Test
    @DisplayName("콘서트 예약 날짜 조회")
    void checkConcertDate() {
        int concertId = 1;
        List<Concert_item> concertItems = new ArrayList<>();
        concertItems.add(new Concert_item()); // 여기에 실제 Concert_item 객체를 추가

        when(concertItemRepository.findByConcertId(concertId)).thenReturn(concertItems);

        List<Concert_item> result = concertService.checkConcertDate(concertId);

        assertThat(result).isEqualTo(concertItems);
        verify(concertItemRepository, times(1)).findByConcertId(concertId);
    }

    @Test
    @DisplayName("콘서트 예약 좌석 조회")
    void checkConcertSeat() {
        int itemId = 1;
        List<Concert_seat> concertSeats = new ArrayList<>();
        concertSeats.add(new Concert_seat()); // 여기에 실제 Concert_seat 객체를 추가

        when(concertSeatRepository.findAllByItemId(itemId)).thenReturn(concertSeats);

        List<Concert_seat> result = concertService.checkConcertSeat(itemId);

        assertThat(result).isEqualTo(concertSeats);
        verify(concertSeatRepository, times(1)).findAllByItemId(itemId);
    }
}