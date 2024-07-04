package org.hhplus.clean_architecture.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "point")
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    @Id
    private int pointId;
    private String userId;
    private int point;
}
