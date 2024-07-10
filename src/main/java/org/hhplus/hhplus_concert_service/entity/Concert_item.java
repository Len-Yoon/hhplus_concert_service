package org.hhplus.hhplus_concert_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Table (name = "concertItem")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Concert_item {
    @Id
    private int itemId;
    private int concertId;
    private LocalDateTime concertDate;

}
