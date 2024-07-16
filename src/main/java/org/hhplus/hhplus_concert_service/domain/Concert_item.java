package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

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
