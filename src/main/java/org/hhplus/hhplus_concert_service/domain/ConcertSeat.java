package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Table(name = "concertSeat")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class ConcertSeat {
    @Id
    @NotNull(message = "seatId cannot be empty.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    private int itemId;
    private int seatNum;
    private String status;
    private int seatPrice;

    //낙관적 락 사용
    @Version
    private int version;
}
