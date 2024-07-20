package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Table(name = "Point")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Point {
    @Id
    @NotNull(message = "pointId cannot be empty.")
    int pointId;
    private String userId;
    private int point;
}
