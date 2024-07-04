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
@Table(name = "concsertSeat")
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat {
    @Id
    private int concertSeatId;
    private int concertId;
    private int seatNum;
    private int price;
    private LocalDateTime perpormenceAt;
    private String status;
    private LocalDateTime createAt;
}
