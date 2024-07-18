package org.hhplus.hhplus_concert_service.unitTest;

import org.hhplus.hhplus_concert_service.business.PaymentServiceImpl;
import org.hhplus.hhplus_concert_service.domain.Payment;
import org.hhplus.hhplus_concert_service.persistence.Payment_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {

    @Mock
    private Payment_repository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("결제 신청")
    void payment() {
        int paymentAmount = 100;
        int reservationId = 1;

        paymentService.payment(paymentAmount, reservationId);

        Payment expectedPayment = new Payment();
        expectedPayment.setReservationId(reservationId);
        expectedPayment.setPaymentAmount(paymentAmount);

        verify(paymentRepository, times(1)).save(expectedPayment);
    }

    @Test
    @DisplayName("결제 금액이 0 이하인 경우 예외 발생")
    void paymentWithInvalidAmount() {
        int paymentAmount = 0;
        int reservationId = 1;

        // 결제 금액이 0 이하인 경우 예외 발생을 확인
        assertThrows(RuntimeException.class, () -> {
            paymentService.payment(paymentAmount, reservationId);
        });
    }
}