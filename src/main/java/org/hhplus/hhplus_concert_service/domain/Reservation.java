package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Reservation {
    @Id
    private int reservationId;
    private String userId;
    private int concertId;
    private int seatId;
    private int itemId;
    private int paymentId;
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;

}
