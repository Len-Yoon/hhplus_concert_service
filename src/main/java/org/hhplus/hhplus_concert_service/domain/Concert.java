package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Data
@Table (name = "concert")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Concert {
    @Id
    @NotNull(message = "concertId cannot be empty.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int concertId;
    private String title;
    private String status;
    private LocalDateTime createdAt;


}
