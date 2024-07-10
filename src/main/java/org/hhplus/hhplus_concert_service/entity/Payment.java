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
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Payment {
    @Id
    private int paymentId;
    private int reservationId;
    private int paymentAmount;
    private LocalDateTime  createdAt;

}
