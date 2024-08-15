package org.hhplus.hhplus_concert_service.business.service.event.concert;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnChangeSeatStatusEvent extends ApplicationEvent {
    private final int seatId;

    public OnChangeSeatStatusEvent(Object source, int seatId) {
        super(source);
        this.seatId = seatId;
    }

    public int getSeatId() {
        return seatId;
    }
}
