package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.hhplus.hhplus_concert_service.interfaces.controller.dto.TokenQueueDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
@Valid
public class TokenController {

    private final TokenQueueService tokenQueueService;

//    @PostMapping("/generate")
//    public void generateTokenForUser(@Valid @ModelAttribute TokenQueueDTO tokenQueueDTO) {
//        String userId = tokenQueueDTO.getUserId();
//        tokenQueueService.generateTokenForUser(userId);
//    }
//
//    @GetMapping("/getToken")
//    public TokenQueue getToken(@Valid @ModelAttribute TokenQueueDTO tokenQueueDTO) {
//        String status = tokenQueueDTO.getStatus();
//        return tokenQueueService.getToken(status);
//    }

    // 대기열 사용자 추가
    @PostMapping("/add")
    public void addTokenQueue(@Valid @ModelAttribute TokenQueueDTO tokenQueueDTO) {
        String userId = tokenQueueDTO.getUserId();
        int concertId = tokenQueueDTO.getConcertId();

        tokenQueueService.addTokenQueue(userId, concertId);
    }
}
