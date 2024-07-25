package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Payment;
import org.hhplus.hhplus_concert_service.persistence.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public void payment(int paymentAmount, int reservationId) {
        Payment payment = new Payment();

        payment.setReservationId(reservationId);
        payment.setPaymentAmount(paymentAmount);

        paymentRepository.save(payment);
    }
}
