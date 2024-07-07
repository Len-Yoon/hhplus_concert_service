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
@Table (name = "concert_item")
@AllArgsConstructor
@NoArgsConstructor
public class Concert_item {
    @Id
    private int item_id;
    private int concert_id;
    private LocalDateTime concert_date;
}
