package org.hhplus.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tokenQueue")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class TokenQueue {
    @Id
    private int queueId;
    private String userId;
    private String token;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime issuedAt;
    private boolean Active;

    public TokenQueue() {}

    public TokenQueue(String userId) {
        this.userId = userId;
        this.Active = false;
    }

}
