package org.hhplus.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "concert_seat")
@AllArgsConstructor
@NoArgsConstructor
public class Concert_seat {
    @Id
    private int seat_id;
    private int item_id;
    private int seat_num;
    private String status;
    private int seat_price;
}
