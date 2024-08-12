package org.hhplus.hhplus_concert_service.business.service.event.tokenQueue;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnDeleteTokenEvent extends ApplicationEvent {
    private String userId;
    private int concertId;

    public OnDeleteTokenEvent(Object source, String userId, int concertId) {
        super(source);
        this.userId = userId;
        this.concertId = concertId;
    }

    public String getUserId() {
        return userId;
    }

    public int getConcertId() {
        return concertId;
    }
}
