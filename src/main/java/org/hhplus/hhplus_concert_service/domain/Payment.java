package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

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
