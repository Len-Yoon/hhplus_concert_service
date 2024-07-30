package org.hhplus.hhplus_concert_service.unitTest;

import org.hhplus.hhplus_concert_service.business.service.ConcertServiceImpl;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.persistence.ConcertItemRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
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
    private ConcertRepository concertRepository;

    @Mock
    private ConcertItemRepository concertItemRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

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
        List<ConcertItem> concertItems = new ArrayList<>();
        concertItems.add(new ConcertItem()); // 여기에 실제 Concert_item 객체를 추가

        when(concertItemRepository.findByConcertId(concertId)).thenReturn(concertItems);

        List<ConcertItem> result = concertService.checkConcertDate(concertId);

        assertThat(result).isEqualTo(concertItems);
        verify(concertItemRepository, times(1)).findByConcertId(concertId);
    }

    @Test
    @DisplayName("콘서트 예약 좌석 조회")
    void checkConcertSeat() {
        int itemId = 1;
        List<ConcertSeat> concertSeats = new ArrayList<>();
        concertSeats.add(new ConcertSeat()); // 여기에 실제 Concert_seat 객체를 추가

        when(concertSeatRepository.findAllByItemId(itemId)).thenReturn(concertSeats);

        List<ConcertSeat> result = concertService.checkConcertSeat(itemId);

        assertThat(result).isEqualTo(concertSeats);
        verify(concertSeatRepository, times(1)).findAllByItemId(itemId);
    }
}