package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.Utils;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.business.TokenQueueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenQueueService tokenQueueService;

    @PostMapping("/generate")
    public void generateTokenForUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = Utils.checkNull(request.getParameter("userId"));
        tokenQueueService.generateTokenForUser(userId);
    }

    @GetMapping("/all")
    public List<TokenQueue> getAllTokens() {
        return tokenQueueService.getAllTokens();
    }

}
