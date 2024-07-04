package org.hhplus.clean_architecture.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pointHistory")
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory {
    @Id
    private int pointHistoryId;
    private int pointId;
    private String userId;
    private int paymentId;
    private LocalDateTime createAt;
}
