package org.hhplus.hhplus_concert_service.business.service.event.payment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnPaymentEvent extends ApplicationEvent {
    private final int totalPrice;
    private final int reservationId;


    public OnPaymentEvent(Object source, int totalPrice, int reservationId) {
        super(source);
        this.totalPrice = totalPrice;
        this.reservationId = reservationId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getReservationId() {
        return reservationId;
    }
}
