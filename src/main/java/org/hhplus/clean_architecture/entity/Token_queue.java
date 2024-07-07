package org.hhplus.clean_architecture.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "token_queue")
@AllArgsConstructor
@NoArgsConstructor
public class Token_queue {
    @Id
    private int queue_id;
    private int token_id;
    private boolean status;
    private LocalDateTime created_at;
}
