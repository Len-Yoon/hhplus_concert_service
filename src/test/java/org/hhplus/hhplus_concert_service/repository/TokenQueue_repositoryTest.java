package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.TokenQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenQueue_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(TokenQueue_repositoryTest.class);

    @Autowired
    TokenQueue_repository tokenQueueRepository;

    @Test
    @DisplayName("토큰 발급 테스트")
    void insertDataTest() {
        TokenQueue tokenQueue = new TokenQueue();

        tokenQueue.setUserId("유저1");
        tokenQueue.setStatus("대기");
        tokenQueue.setCreatedAt(LocalDateTime.now());
        tokenQueue.setActive(false);

        tokenQueueRepository.save(tokenQueue);
    }




}