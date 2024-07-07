package org.hhplus.hhplus_concert_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "token")
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    private int token_id;
    private String user_id;
    private LocalDateTime created_at;
}
