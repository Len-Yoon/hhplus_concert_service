package org.hhplus.hhplus_concert_service.business;

public interface PaymentService  {

    //결제 신청
    void payment(int paymentAmount, int reservationId);
}
