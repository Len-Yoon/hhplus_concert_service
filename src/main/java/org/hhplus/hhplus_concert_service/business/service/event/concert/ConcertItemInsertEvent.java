package org.hhplus.hhplus_concert_service.business.service.event.concert;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

@Getter
public class ConcertItemInsertEvent extends ApplicationEvent {
    private final int concertId;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public ConcertItemInsertEvent(Object source, int concertId, LocalDate startDate, LocalDate endDate) {
        super(source);
        this.concertId = concertId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getConcertId() {
        return concertId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
