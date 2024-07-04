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
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    private int reservationNum;
    private int concertSeatId;
    private int paymentId;
    private String userId;
    private LocalDateTime createAt;
}
