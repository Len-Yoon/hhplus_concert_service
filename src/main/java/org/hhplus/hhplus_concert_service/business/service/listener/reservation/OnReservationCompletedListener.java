package org.hhplus.hhplus_concert_service.business.service.listener.reservation;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.ReservationService;
import org.hhplus.hhplus_concert_service.business.service.event.reservation.OnReservationCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnReservationCompletedListener {

    private final ReservationService reservationService;
    private static final Logger log = LoggerFactory.getLogger(OnReservationCompletedListener.class);

    @KafkaListener(topics = "reservation_topic", groupId = "reservation-group")
    public void handleReservation(OnReservationCompletedEvent event) {
        if(event == null) {
            log.error("Received null event");
            throw new IllegalArgumentException("ReservationEvent cannot be null");
        }

        try {
            String userId = event.getUserId();
            int concertId = event.getConcertId();
            int reservationId = event.getReservationId();
            int paymentId = event.getPaymentId();

            reservationService.reservationCompleted(userId, concertId, reservationId, paymentId);
        } catch (RuntimeException e) {
            log.error("Failed to handle ReservationEvent", e);
            throw new RuntimeException("ReservationEvent processing failed", e);
        }
    }
}
