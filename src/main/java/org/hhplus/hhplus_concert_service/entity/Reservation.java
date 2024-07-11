package org.hhplus.hhplus_concert_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private int payment_id;
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;

}
