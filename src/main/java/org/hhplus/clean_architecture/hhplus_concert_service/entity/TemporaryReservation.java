package org.hhplus.clean_architecture.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "teporaryReservation")
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryReservation {
    @Id
    private int temporaryReservationId;
    private int concertSeatId;
    private String userId;
}
