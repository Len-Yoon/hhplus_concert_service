package org.hhplus.hhplus_concert_service.interfaces.controller.dto;

import jakarta.validation.constraints.NotNull;

public class PointDTO {
    @NotNull(message = "userId must not be null")
    private String userId;
    @NotNull(message = "chargePoint must not be null")
    private int chargePoint;
    @NotNull(message = "totalPoint must not be null")
    private int totalPoint;
    @NotNull(message = "concertId must not be null")
    private int concertId;
    @NotNull(message = "reservationId must not be null")
    private int reservationId;

    public @NotNull(message = "userId must not be null") String getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "userId must not be null") String userId) {
        this.userId = userId;
    }

    @NotNull(message = "chargePoint must not be null")
    public int getChargePoint() {
        return chargePoint;
    }

    public void setChargePoint(@NotNull(message = "chargePoint must not be null") int chargePoint) {
        this.chargePoint = chargePoint;
    }

    @NotNull(message = "totalPoint must not be null")
    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(@NotNull(message = "totalPoint must not be null") int totalPoint) {
        this.totalPoint = totalPoint;
    }

    @NotNull(message = "concertId must not be null")
    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(@NotNull(message = "concertId must not be null") int concertId) {
        this.concertId = concertId;
    }

    @NotNull(message = "reservationId must not be null")
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(@NotNull(message = "reservationId must not be null") int reservationId) {
        this.reservationId = reservationId;
    }
}
