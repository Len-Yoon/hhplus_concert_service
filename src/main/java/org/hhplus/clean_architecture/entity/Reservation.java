package org.hhplus.clean_architecture.entity;

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
    private int reservation_id;
    private String user_id;
    private int concert_id;
    private int seat_id;
    private int item_id;
    private int total_price;
    private String status;
    private LocalDateTime created_at;
}
