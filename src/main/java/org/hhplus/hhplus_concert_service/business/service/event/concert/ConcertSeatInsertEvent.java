package org.hhplus.hhplus_concert_service.business.service.event.concert;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ConcertSeatInsertEvent extends ApplicationEvent {
    private final int itemId;
    private final int seatSize;
    private final String seatPrice;
    private final String status;


    public ConcertSeatInsertEvent(Object source, int itemId, int seatSize, String seatPrice, String status) {
        super(source);
        this.itemId = itemId;
        this.seatSize = seatSize;
        this.seatPrice = seatPrice;
        this.status = status;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSeatSize() {
        return seatSize;
    }

    public String getSeatPrice() {
        return seatPrice;
    }

    public String getStatus() {
        return status;
    }
}
