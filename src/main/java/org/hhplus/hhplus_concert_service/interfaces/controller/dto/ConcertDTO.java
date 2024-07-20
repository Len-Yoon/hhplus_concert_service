package org.hhplus.hhplus_concert_service.interfaces.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ConcertDTO {

    @NotNull(message = "concertId must not be null")
    private int concertId;
    @NotNull(message = "itemId must not be null")
    private int itemId;
    @NotNull(message = "checkDate must not be null")
    private LocalDate checkDate;

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }
}
