package org.hhplus.hhplus_concert_service.integrationTest;

import org.hhplus.hhplus_concert_service.business.service.ConcertService;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.interfaces.controller.ConcertController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcertService concertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkConcert() throws Exception {
        List<Concert> concerts = Collections.singletonList(new Concert());
        when(concertService.checkConcert()).thenReturn(concerts);

        mockMvc.perform(get("/concert"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

    }

    @Test
    void checkConcertDate() throws Exception {

        List<ConcertItem> concertItems = Collections.singletonList(new ConcertItem());
        when(concertService.checkConcertDate(any(Integer.class))).thenReturn(concertItems);

        mockMvc.perform(get("/concert/concertDate").param("concertId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    void checkConcertSeat() throws Exception {

        List<ConcertSeat> concertSeats = Collections.singletonList(new ConcertSeat());
        when(concertService.checkConcertSeat(any(Integer.class))).thenReturn(concertSeats);

        mockMvc.perform(get("/concert/concertSeat").param("concertItemId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }

    @Test
    void checkConcertByConcertDate() throws Exception {
        List<Concert> concerts = Collections.singletonList(new Concert());
        when(concertService.checkConcertByConcertDate(any(LocalDate.class))).thenReturn(concerts);

        mockMvc.perform(get("/concert/concertByDate").param("checkDate", "2024-07-17"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }
}