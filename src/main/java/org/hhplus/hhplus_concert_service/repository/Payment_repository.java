package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface Payment_repository extends JpaRepository<Payment, Integer> {

    //특정 결제 내역 조회
    Payment findByReservationId(int reservationId);

    //결제 내역 삭제
    Payment deleteByPaymentId(int paymentId);
}
