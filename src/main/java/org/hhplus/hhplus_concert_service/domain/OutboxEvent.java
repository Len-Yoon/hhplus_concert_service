package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;

@Entity
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private String payload;

    @Column(name = "in_progress")
    private boolean inProgress;  // 중복 발행 방지용 필드

    public OutboxEvent() {}

    public OutboxEvent(String eventType, String payload) {
        this.eventType = eventType;
        this.payload = payload;
    }

    private boolean processed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }


}
