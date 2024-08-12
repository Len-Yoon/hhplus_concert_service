package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Payment;
import org.hhplus.hhplus_concert_service.persistence.PaymentRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public void payment(int paymentAmount, int reservationId) {
        try {
            Payment payment = new Payment();

            payment.setReservationId(reservationId);
            payment.setPaymentAmount(paymentAmount);

            paymentRepository.save(payment);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
