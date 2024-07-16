package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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
        tokenQueue.setIssuedAt(LocalDateTime.now());
        tokenQueue.setActive(false);

        tokenQueueRepository.save(tokenQueue);
    }




}