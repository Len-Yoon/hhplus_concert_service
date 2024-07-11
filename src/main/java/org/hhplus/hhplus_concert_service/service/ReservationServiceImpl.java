package org.hhplus.hhplus_concert_service.service;

import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.entity.Concert;
import org.hhplus.hhplus_concert_service.entity.Concert_seat;
import org.hhplus.hhplus_concert_service.entity.Reservation;
import org.hhplus.hhplus_concert_service.repository.Concert_repository;
import org.hhplus.hhplus_concert_service.repository.Concert_seat_repository;
import org.hhplus.hhplus_concert_service.repository.Reservation_repository;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final Concert_repository concertRepository;
    private final Concert_seat_repository concertSeatRepository;
    private final Reservation_repository reservationRepository;

    @Override
    @Transactional
    @Retryable(retryFor ={OptimisticLockException.class, StaleObjectStateException.class,
            ObjectOptimisticLockingFailureException.class}, maxAttempts = 5, backoff = @Backoff(delay = 100))
    public void reservation(String userId, int concertId, int itemId, int seatId, int totalPrice, String status) {

        Concert concert = concertRepository.findByConcertId(concertId);
        Concert_seat concertSeat = concertSeatRepository.findBySeatId(seatId);

        String concertStatus = concert.getStatus();
        String seatstatus = concertSeat.getStatus();

        if(!concertStatus.equals("Y")) {
            throw new RuntimeException("예약 가능한 콘서트가 아닙니다.");
        } else if (!seatstatus.equals("예약가능")) {
            throw new RuntimeException("이미 예약이 된 좌석입니다.");
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
        }
    }

    @Override
    public void reservationCompleted(int reservationId, int paymentId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        reservation.setStatus("예약완료");
        reservation.setPayment_id(paymentId);

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
