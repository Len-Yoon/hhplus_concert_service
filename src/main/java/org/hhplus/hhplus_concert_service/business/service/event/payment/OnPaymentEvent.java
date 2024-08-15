package org.hhplus.hhplus_concert_service.business.service.event.payment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnPaymentEvent extends ApplicationEvent {
    private int reservationId;
    private int paymentAmount;


    public OnPaymentEvent(Object source, int reservationId, int paymentAmount) {
        super(source);
        this.reservationId = reservationId;
        this.paymentAmount = paymentAmount;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }
}
