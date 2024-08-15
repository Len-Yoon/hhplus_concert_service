package org.hhplus.hhplus_concert_service.business.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hhplus.hhplus_concert_service.business.constans.ReservationConstants;
import org.hhplus.hhplus_concert_service.business.service.event.concert.OnChangeSeatStatusEvent;
import org.hhplus.hhplus_concert_service.business.service.event.reservation.OnReservationCompletedEvent;
import org.hhplus.hhplus_concert_service.business.service.event.tokenQueue.OnDeleteTokenEvent;
import org.hhplus.hhplus_concert_service.business.service.listener.tokenQueue.OnDeleteTokenListener;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.domain.OutboxEvent;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.persistence.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ConcertRepository concertRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ReservationRepository reservationRepository;
    private final OutBoxEventRepository outboxEventRepository;

    private final EntityManager entityManager;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void reservation(String userId, int concertId, int itemId, int seatId, int totalPrice, String status) {

        int retryCount = 5;

        while (retryCount > 0) {
            Concert concert = concertRepository.findByConcertId(concertId);

            //비관적락 사용 위한 select문
            ConcertSeat concertSeat = concertSeatRepository.lockSeatById(seatId);

            concertSeat.setStatus("N");
            concertSeatRepository.save(concertSeat);

            if (concertSeat == null) {
                throw new NoSuchElementException();
            }
            if (!ReservationConstants.SEAT_AVAILABLE.equals(concertSeat.getStatus())) {
                throw new IllegalStateException();
            }

            try {
                if (concert == null) {
                    throw new NoSuchElementException();
                }
                if (!ReservationConstants.CONCERT_AVAILABLE.equals(concert.getStatus())) {
                    throw new IllegalStateException();
                }

                Reservation reservation = new Reservation();

                reservation.setUserId(userId);
                reservation.setConcertId(concertId);
                reservation.setSeatId(seatId);
                reservation.setItemId(itemId);
                reservation.setTotalPrice(totalPrice);
                reservation.setStatus("T");

                reservationRepository.save(reservation);

                // 이벤트를 데이터베이스에 저장 (Outbox 패턴 적용)
                String eventType = "CHANGE_SEAT_STATUS";
                String payload = String.format("{\"seatId\": %d}", seatId);
                OutboxEvent outboxEvent = new OutboxEvent(eventType, payload);
                outboxEventRepository.save(outboxEvent);
                eventPublisher.publishEvent(new OnChangeSeatStatusEvent(this, seatId));

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

    public void reservationCompleted(String userId, int concertId, int reservationId, int paymentId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        if (reservation == null) {
            throw new NoSuchElementException("Reservation not found for id: " + reservationId);
        }

        reservation.setStatus("Y");
        reservation.setPaymentId(paymentId);

        reservationRepository.save(reservation);

        try {
            // 아웃박스 이벤트 생성
            String eventType = "RESERVATION_EVENT";
            String payload = String.format("{\"userId\": \"%s\", \"concertId\": %d}", userId, concertId);

            OutboxEvent outboxEvent = new OutboxEvent(eventType, payload);
            outboxEventRepository.save(outboxEvent);

            eventPublisher.publishEvent(new OnDeleteTokenEvent(this,userId,concertId));

        } catch (Exception e) {
            log.error("Failed to create outbox event for reservation completion", e);
            throw new RuntimeException("Failed to complete reservation due to event processing error", e);
        }
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
