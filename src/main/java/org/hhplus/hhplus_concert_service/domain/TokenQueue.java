package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "queueId cannot be empty.")
    private int queueId;
    @NotNull(message = "userId cannot be empty.")
    private String userId;
    private String token;
    private String status;
    private LocalDateTime issuedAt;
    private boolean active;

}
