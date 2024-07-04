package org.hhplus.clean_architecture.hhplus_concert_service.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class issuance {
    private String userId;
    private int tokenId;
    private int watingNum;
    private String status;
}
