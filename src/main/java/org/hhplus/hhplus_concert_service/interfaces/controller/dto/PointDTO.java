package org.hhplus.hhplus_concert_service.interfaces.controller.dto;

import jakarta.validation.constraints.NotNull;

public class PointDTO {
    @NotNull(message = "userId must not be null")
    private String userId;
    @NotNull(message = "chargePoint must not be null")
    private int chargePoint;
    @NotNull(message = "totalPoint must not be null")
    private int totalPoint;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChargePoint() {
        return chargePoint;
    }

    public void setChargePoint(int chargePoint) {
        this.chargePoint = chargePoint;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }
}
