package org.hhplus.hhplus_concert_service.unitTest;

import org.hhplus.hhplus_concert_service.business.service.ReservationServiceImpl;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.persistence.Concert_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_seat_repository;
import org.hhplus.hhplus_concert_service.persistence.Reservation_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReservationServiceTest {

    @Mock
    private Concert_repository concertRepository;

    @Mock
    private Concert_seat_repository concertSeatRepository;

    @Mock
    private Reservation_repository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("예약 요청")
    void reservation() {
        String userId = "유저1";
        int concertId = 1;
        int itemId = 1;
        int seatId = 1;
        int totalPrice = 100;
        String status = "임시예약";

        Concert concert = new Concert();
        concert.setStatus("Y");
        when(concertRepository.findByConcertId(concertId)).thenReturn(concert);

        Concert_seat concertSeat = new Concert_seat();
        concertSeat.setStatus("예약가능");
        when(concertSeatRepository.findBySeatId(seatId)).thenReturn(concertSeat);

        reservationService.reservation(userId, concertId, itemId, seatId, totalPrice, status);

        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(concertSeatRepository, times(1)).save(concertSeat);
        assertEquals("예약완료", concertSeat.getStatus());

    }

    @Test
    @DisplayName("예약 요청 Exception")
    public void reservation_ConcertNotAvailable() {
        String userId = "유저1";
        int concertId = 1;
        int itemId = 1;
        int seatId = 1;
        int totalPrice = 100;

        Concert concert = new Concert();
        concert.setStatus("N");
        when(concertRepository.findByConcertId(concertId)).thenReturn(concert);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.reservation(userId, concertId, itemId, seatId, totalPrice, "임시예약");
        });

        assertEquals("예약 가능한 콘서트가 아닙니다.", exception.getMessage());
    }

    @Test
    @DisplayName("예약 완료")
    void reservationCompleted() {
        int reservationId = 1;
        int paymentId = 1;

        Reservation reservation = new Reservation();
        reservation.setStatus("임시예약");
        when(reservationRepository.findByReservationId(reservationId)).thenReturn(reservation);

        reservationService.reservationCompleted(reservationId, paymentId);

        verify(reservationRepository, times(1)).save(reservation);
        assertEquals("예약완료", reservation.getStatus());
        assertEquals(paymentId, reservation.getPaymentId());

    }

    @Test
    @DisplayName("특정 유저 예약 내역 조회")
    void checkReservations() {
        String userId = "유저1";
        List<Reservation> reservations = Collections.singletonList(new Reservation());
        when(reservationRepository.findAllByUserId(userId)).thenReturn(reservations);

        List<Reservation> result = reservationService.checkReservations(userId);

        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void checkAllReservations() {
        String status = "임시예약";
        List<Reservation> reservations = Collections.singletonList(new Reservation());
        when(reservationRepository.findAllByStatus(status)).thenReturn(reservations);

        List<Reservation> result = reservationService.checkAllReservations(status);

        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findAllByStatus(status);
    }
}