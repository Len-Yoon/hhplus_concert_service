package org.hhplus.hhplus_concert_service.business.service.event.concert;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class OnChangeConcertSeatStatusEvent extends ApplicationEvent {
    private final int seatId;

    public OnChangeConcertSeatStatusEvent(Object source, int seatId) {
        super(source);
        this.seatId = seatId;
    }

    public int getSeatId() {
        return seatId;
    }
}
