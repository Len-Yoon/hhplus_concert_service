package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
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
public class Concert_seat {
    @Id
    private int seatId;
    private int itemId;
    private int seatNum;
    private String status;
    private int seatPrice;

    @Version
    private int version;
}
