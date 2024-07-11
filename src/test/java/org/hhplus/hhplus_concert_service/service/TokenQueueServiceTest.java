package org.hhplus.hhplus_concert_service.service;

import org.hhplus.hhplus_concert_service.repository.TokenQueue_repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenQueueServiceTest {

    @Autowired
    TokenQueueService tokenQueueService;

    @Test
    void generateTokenForUser() {
        String userId = "유저1";

        tokenQueueService.generateTokenForUser(userId);
    }

    @Test
    void issueTokens() {
        tokenQueueService.issueTokens();
    }

    @Test
    void getToken() {
        String userId = "유저1";
        tokenQueueService.getToken(userId);
    }

    @Test
    void deleteToken() {
        int queueId = 1;
        tokenQueueService.deleteToken(queueId);
    }
}