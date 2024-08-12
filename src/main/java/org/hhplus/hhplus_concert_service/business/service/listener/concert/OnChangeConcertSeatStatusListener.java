package org.hhplus.hhplus_concert_service.business.service.listener.concert;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.ConcertService;
import org.hhplus.hhplus_concert_service.business.service.event.concert.OnChangeConcertSeatStatusEvent;
import org.hhplus.hhplus_concert_service.business.service.listener.payment.OnPaymentListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnChangeConcertSeatStatusListener {

    private final ConcertService concertService;
    private static final Logger log = LoggerFactory.getLogger(OnPaymentListener.class);

    @EventListener
    public void handleChangeConcertSeat(OnChangeConcertSeatStatusEvent event) {
        if(event == null) {
            log.error("Received null event");
            throw new IllegalArgumentException("Event cannot be null");
        }

        try {
            int seatId = event.getSeatId();

            concertService.concertStatusChange(seatId);
        } catch (RuntimeException e) {
            log.error("Failed to handle payment event", e);
            throw new RuntimeException("OnSeatStatusChange processing failed", e);
        }
    }
}
