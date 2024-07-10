package org.hhplus.hhplus_concert_service.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Test
    @DisplayName("결제 신청")
    void payment() {
        int paymentAmount = 50000;
        int reservationId = 1;

        paymentService.payment(paymentAmount, reservationId);
    }
}