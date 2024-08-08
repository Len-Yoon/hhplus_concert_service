package org.hhplus.hhplus_concert_service.business.service.event.point;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PointPlusEvent extends ApplicationEvent {
    private final String userId;
    private final int chargePoint;


    public PointPlusEvent(Object source, String userId, int chargePoint) {
        super(source);
        this.userId = userId;
        this.chargePoint = chargePoint;
    }

    public String getUserId() {
        return userId;
    }

    public int getChargePoint() {
        return chargePoint;
    }
}
