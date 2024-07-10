package org.hhplus.hhplus_concert_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface PaymentService  {

    //결제 신청
    void payment(int paymentAmount, int reservationId);
}
