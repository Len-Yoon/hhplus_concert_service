package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.business.TokenQueueService;
import org.hhplus.hhplus_concert_service.interfaces.controller.dto.TokenQueueDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
@Valid
public class TokenController {

    private final TokenQueueService tokenQueueService;

    @PostMapping("/generate")
    public void generateTokenForUser(@Valid @ModelAttribute TokenQueueDTO tokenQueueDTO) {
        String userId = tokenQueueDTO.getUserId();
        tokenQueueService.generateTokenForUser(userId);
    }

    @GetMapping("/all")
    public List<TokenQueue> getAllTokens() {
        return tokenQueueService.getAllTokens();
    }

}
