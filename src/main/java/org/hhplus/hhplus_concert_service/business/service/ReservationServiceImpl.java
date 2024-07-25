package org.hhplus.hhplus_concert_service.business.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.constans.ReservationConstants;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
import org.hhplus.hhplus_concert_service.persistence.ReservationRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ConcertRepository concertRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ReservationRepository reservationRepository;

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void reservation(String userId, int concertId, int itemId, int seatId, int totalPrice, String status) {

        int retryCount = 5;

        while (retryCount > 0) {
            Concert concert = concertRepository.findByConcertId(concertId);
            //낙관적 락을 사용
            ConcertSeat concertSeat = concertSeatRepository.findBySeatId(seatId);

            try {
                if (concert == null) {
                    throw new NoSuchElementException();
                }
                if (!ReservationConstants.CONCERT_AVAILABLE.equals(concert.getStatus())) {
                    throw new IllegalStateException();
                }

                if (concertSeat == null) {
                    throw new NoSuchElementException();
                }
                if (!ReservationConstants.SEAT_AVAILABLE.equals(concertSeat.getStatus())) {
                    throw new IllegalStateException();
                }

                //비관적락 사용 위한 select문
                concertSeatRepository.lockSeatById(seatId);

                Reservation reservation = new Reservation();

                reservation.setUserId(userId);
                reservation.setConcertId(concertId);
                reservation.setSeatId(seatId);
                reservation.setItemId(itemId);
                reservation.setTotalPrice(totalPrice);
                reservation.setStatus("T");

                reservationRepository.save(reservation);

                concertSeat.setStatus("N");

                concertSeatRepository.save(concertSeat);
                break;


            } catch (ObjectOptimisticLockingFailureException e) {
                //낙관적 락 캐쉬 초기화
                entityManager.clear();
                retryCount --;
                if(retryCount == 0) {
                    throw new RuntimeException("This seat is reserved");
                }
            } catch (PessimisticLockException | LockTimeoutException e) {
                throw new RuntimeException("This seat is reserved");
            }
        }
    }

    @Override
    public void reservationCompleted(int reservationId, int paymentId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        reservation.setStatus("Y");
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
