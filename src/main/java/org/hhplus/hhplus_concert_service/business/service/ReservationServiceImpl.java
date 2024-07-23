package org.hhplus.hhplus_concert_service.business.service;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.constans.ReservationConstants;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.persistence.Concert_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_seat_repository;
import org.hhplus.hhplus_concert_service.persistence.Reservation_repository;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final Concert_repository concertRepository;
    private final Concert_seat_repository concertSeatRepository;
    private final Reservation_repository reservationRepository;

    @Override
    //낙관적 락 적용
    public void reservation(String userId, int concertId, int itemId, int seatId, int totalPrice, String status) {
        int retryCount = 5;

        Concert concert = concertRepository.findByConcertId(concertId);
        Concert_seat concertSeat = concertSeatRepository.findBySeatId(seatId);

        String concertStatus = concert.getStatus();
        String seatStatus = concertSeat.getStatus();

        while (retryCount > 0) {
            try {

                if(!ReservationConstants.CONCERT_AVAILABLE.equals(concertStatus)) {
                    throw new NoSuchElementException();
                } else if (!ReservationConstants.SEAT_AVAILABLE.equals(seatStatus)) {
                    throw new NoSuchElementException();
                } else {
                    Reservation reservation = new Reservation();

                    reservation.setUserId(userId);
                    reservation.setConcertId(concertId);
                    reservation.setSeatId(seatId);
                    reservation.setItemId(itemId);
                    reservation.setTotalPrice(totalPrice);
                    reservation.setStatus("임시예약");

                    reservationRepository.save(reservation);

                    concertSeat.setStatus("예약완료");

                    concertSeatRepository.save(concertSeat);
                    break;
                }
            } catch (ObjectOptimisticLockingFailureException e) {
                retryCount --;
                if(retryCount == 0) {
                    throw new RuntimeException();
                }
            }
        }

    }

    @Override
    public void reservationCompleted(int reservationId, int paymentId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        reservation.setStatus("예약완료");
        reservation.setPaymentId(paymentId);

        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> checkReservations(String userId) {
        return reservationRepository.findAllByUserId(userId);
    }

    @Override
    public List<Reservation> checkAllReservations(String status) {
        return reservationRepository.findAllByStatus(status);
    }
}
