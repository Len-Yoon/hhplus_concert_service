package org.hhplus.hhplus_concert_service.integrationTest;

import org.hhplus.hhplus_concert_service.business.service.ReservationService;
import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.interfaces.controller.ReservationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private TokenQueueService tokenQueueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reservation() throws Exception {
        doNothing().when(reservationService).reservation(any(String.class), any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class), any(String.class));

        mockMvc.perform(post("/reservation")
                        .param("concertId", "1")
                        .param("itemId", "1")
                        .param("seatId", "1")
                        .param("userId", "testUser")
                        .param("totalPrice", "100"))
                .andExpect(status().isOk());
    }

    @Test
    void reservationCompleted() throws Exception {
        doNothing().when(reservationService).reservationCompleted(any(Integer.class), any(Integer.class));

        mockMvc.perform(post("/reservation/reservationCompleted")
                        .param("reservationId", "1")
                        .param("paymentId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void checkReservation() throws Exception {
        List<Reservation> reservations = Collections.singletonList(new Reservation());
        when(reservationService.checkReservations(any(String.class))).thenReturn(reservations);

        mockMvc.perform(get("/reservation/checkReservation").param("userId", "유저1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }
}