package org.hhplus.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Point")
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    @Id
    private int point_id;
    private String user_id;
    private int point;
}
