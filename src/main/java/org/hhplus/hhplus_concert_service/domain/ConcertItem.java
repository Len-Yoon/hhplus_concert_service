package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table (name = "concertItem")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class ConcertItem {
    @Id
    @NotNull(message = "itemId cannot be empty.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private int concertId;
    private LocalDate concertDate;

    @Version
    private long version;

}
