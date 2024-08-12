package org.hhplus.hhplus_concert_service.business.service.listener.payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.PaymentService;
import org.hhplus.hhplus_concert_service.business.service.event.payment.OnPaymentEvent;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class OnPaymentListener {

    private final PaymentService paymentService;
    private static final Logger log = LoggerFactory.getLogger(OnPaymentListener.class);

    @EventListener
    public void handlePaymentEvent(OnPaymentEvent event) {
        if(event == null) {
            log.error("Received null event");
            throw new IllegalArgumentException("Event cannot be null");
        }

        try {
            int paymentAmount = event.getTotalPrice();
            int reservationId = event.getReservationId();

            paymentService.payment(paymentAmount, reservationId);
        } catch (RuntimeException e) {
            log.error("Failed to handle payment event", e);
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}
