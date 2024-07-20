package org.hhplus.hhplus_concert_service.interfaces.controller.dto;

import jakarta.validation.constraints.NotNull;

public class ReservationDTO {

    @NotNull(message = "userId must not be null")
    private String userId;
    @NotNull(message = "concertId must not be null")
    private int concertId;
    @NotNull(message = "itemId must not be null")
    private int itemId;
    @NotNull(message = "seatId must not be null")
    private int seatId;
    @NotNull(message = "totalPrice must not be null")
    private int totalPrice;
    @NotNull(message = "reservationId must not be null")
    private int reservationId;
    @NotNull(message = "paymentId must not be null")
    private int paymentId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
