package org.hhplus.hhplus_concert_service.business.service.listener.payment;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.event.payment.PaymentEvent;
import org.hhplus.hhplus_concert_service.domain.Payment;
import org.hhplus.hhplus_concert_service.persistence.PaymentRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentRepository paymentRepository;

    @Async
    @EventListener
    public void handlePaymentEvent(PaymentEvent event) {
        int paymentAmount = event.getPaymentAmount();
        int reservationId = event.getReservationId();

        Payment payment = new Payment();

        payment.setReservationId(reservationId);
        payment.setPaymentAmount(paymentAmount);

        paymentRepository.save(payment);
    }
}
