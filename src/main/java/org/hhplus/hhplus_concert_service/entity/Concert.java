package org.hhplus.hhplus_concert_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Table (name = "concert")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Concert {
    @Id
    private int concertId;
    private String title;
    private String status;
    private LocalDateTime createdAt;

}
