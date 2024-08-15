package org.hhplus.hhplus_concert_service.business.service.listener.payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.PaymentService;
import org.hhplus.hhplus_concert_service.business.service.event.payment.OnPaymentEvent;
import org.hhplus.hhplus_concert_service.business.service.listener.reservation.OnReservationCompletedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnPaymentListener {

    private final PaymentService paymentService;
    private static final Logger log = LoggerFactory.getLogger(OnPaymentListener.class);

    @KafkaListener(topics = "payment_topic", groupId = "payment-group")
    public void handlePayment(OnPaymentEvent event) {
        if(event == null) {
            log.error("Received null event");
            throw new IllegalArgumentException("PaymentEvent cannot be null");
        }

        try {
            int reservationId = event.getReservationId();
            int paymentAmount = event.getPaymentAmount();

            paymentService.payment(paymentAmount, reservationId);
        } catch (RuntimeException e) {
            log.error("Failed to handle payment event", e);
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}
