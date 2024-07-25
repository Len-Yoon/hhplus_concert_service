package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "reservationId cannot be empty.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;
    @NotNull(message = "userId cannot be empty.")
    private String userId;
    private int concertId;
    private int seatId;
    private int itemId;
    private int paymentId;
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;

    @Version
    private int version;

}
