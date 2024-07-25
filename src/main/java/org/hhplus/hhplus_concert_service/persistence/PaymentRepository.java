package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    //특정 결제 내역 조회
    Payment findByReservationId(int reservationId);

    //결제 내역 삭제
    Payment deleteByPaymentId(int paymentId);
}