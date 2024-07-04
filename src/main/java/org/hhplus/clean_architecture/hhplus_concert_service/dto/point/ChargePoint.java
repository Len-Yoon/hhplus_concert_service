package org.hhplus.clean_architecture.hhplus_concert_service.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargePoint {
    private String userId;
    private int point;
}
