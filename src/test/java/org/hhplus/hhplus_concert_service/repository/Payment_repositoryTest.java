package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Payment_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Payment_repositoryTest.class);

    @Autowired
    Payment_repository paymentRepository;

    @Test
    @DisplayName("데이터 입력 테스트")
    void insertDataTest() {
        Payment payment = new Payment();

        payment.setReservationId(1);
        payment.setPaymentAmount(50000);
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);
    }

    @Test
    @DisplayName("결제 결제 전체 내역 조회")
    void paymentTest() {
        List<Payment> paymentList = paymentRepository.findAll();
    }

    @Test
    @DisplayName("특정 결제 내역 조회")
    void paymentInfoTest() {
        Payment payment = paymentRepository.findByReservationId(1);
    }

    @Test
    @DisplayName("결제 내역 삭제")
    void deleteDataTest() {
        paymentRepository.deleteByPaymentId(2);
    }
}