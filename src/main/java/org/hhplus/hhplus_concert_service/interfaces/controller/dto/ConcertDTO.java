package org.hhplus.hhplus_concert_service.interfaces.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConcertDTO {

    @NotNull(message = "concertId must not be null")
    private int concertId;
    @NotNull(message = "itemId must not be null")
    private int itemId;
    @NotNull(message = "checkDate must not be null")
    private LocalDate checkDate;
    @NotNull(message = "seatSize must not be null")
    private int seatSize;
    @NotNull(message = "seatPrice must not be null")
    private String seatPrice;
    @NotNull(message = "status must not be null")
    private String status;
    @NotNull(message = "title must not be null")
    private String title;
    @NotNull(message = "startDate must not be null")
    private LocalDate startDate;
    @NotNull(message = "endDate must not be null")
    private LocalDate endDate;

    @NotNull(message = "concertId must not be null")
    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(@NotNull(message = "concertId must not be null") int concertId) {
        this.concertId = concertId;
    }

    @NotNull(message = "itemId must not be null")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(@NotNull(message = "itemId must not be null") int itemId) {
        this.itemId = itemId;
    }

    public @NotNull(message = "checkDate must not be null") LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(@NotNull(message = "checkDate must not be null") LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    @NotNull(message = "seatSize must not be null")
    public int getSeatSize() {
        return seatSize;
    }

    public void setSeatSize(@NotNull(message = "seatSize must not be null") int seatSize) {
        this.seatSize = seatSize;
    }

    @NotNull(message = "seatPrice must not be null")
    public String getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(@NotNull(message = "seatPrice must not be null") String seatPrice) {
        this.seatPrice = seatPrice;
    }

    public @NotNull(message = "status must not be null") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "status must not be null") String status) {
        this.status = status;
    }

    public @NotNull(message = "title must not be null") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "title must not be null") String title) {
        this.title = title;
    }

    public @NotNull @NotNull(message = "startDate must not be null") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull @NotNull(message = "startDate must not be null") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull @NotNull(message = "endDate must not be null") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull @NotNull(message = "endDate must not be null") LocalDate endDate) {
        this.endDate = endDate;
    }
}
