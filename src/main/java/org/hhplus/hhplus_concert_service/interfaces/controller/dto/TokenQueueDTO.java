package org.hhplus.hhplus_concert_service.interfaces.controller.dto;

import jakarta.validation.constraints.NotNull;

public class TokenQueueDTO {

    @NotNull(message = "userId must not be null")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
