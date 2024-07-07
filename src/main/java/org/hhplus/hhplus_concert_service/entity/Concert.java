package org.hhplus.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table (name = "concert")
@AllArgsConstructor
@NoArgsConstructor
public class Concert {
    @Id
    private int concert_id;
    private String title;
    private LocalDateTime created_at;
}
