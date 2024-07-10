package org.hhplus.hhplus_concert_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.entity.TokenQueue;
import org.hhplus.hhplus_concert_service.service.TokenQueueService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String userId = request.getParameter("userId");
        tokenQueueService.generateTokenForUser(userId);
    }

    @GetMapping("/all")
    public List<TokenQueue> getAllTokens() {
        return tokenQueueService.getAllTokens();
    }

}
