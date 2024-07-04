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
@Table(name = "concert")
@AllArgsConstructor
@NoArgsConstructor
public class Concert {
    @Id
    private int concertId;
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime reservationStartAt;
    private LocalDateTime reservationEndAt;
    private LocalDateTime createAt;
}
