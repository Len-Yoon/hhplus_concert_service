package org.hhplus.hhplus_concert_service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hhplus.hhplus_concert_service.business.constans.ReservationConstants;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

import static org.hhplus.hhplus_concert_service.business.constans.ReservationConstants.CONCERT_AVAILABLE;

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
    @NotNull(message = "title cannot be empty.")
    private String title;
    @NotNull(message = "status cannot be empty.")
    private String status;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
