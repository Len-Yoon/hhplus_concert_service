package org.hhplus.hhplus_concert_service.entity;

import jakarta.persistence.*;
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
    int pointId;
    private String userId;
    private int point;
}
