package org.hhplus.hhplus_concert_service.application;

import org.hhplus.hhplus_concert_service.business.TokenQueueServiceImpl;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.TokenQueue_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TokenQueueServiceTest {

    @Mock
    private TokenQueue_repository tokenQueueRepository;

    @InjectMocks
    private TokenQueueServiceImpl tokenQueueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateTokenForUser() {
        String userId = "유저1";

        when(tokenQueueRepository.countByActiveTrue()).thenReturn(30); // Active tokens less than 50

        tokenQueueService.generateTokenForUser(userId);

        verify(tokenQueueRepository, times(1)).save(any(TokenQueue.class));
    }

    @Test
    public void testGenerateTokenForUser_ActiveCountLimitReached() {
        String userId = "유저1";
        when(tokenQueueRepository.countByActiveTrue()).thenReturn(50); // 50명 카운트

        tokenQueueService.generateTokenForUser(userId);

        verify(tokenQueueRepository, never()).save(any(TokenQueue.class));
    }

    @Test
    void issueTokens() {
        List<TokenQueue> inactiveTokens = new ArrayList<>();
        TokenQueue token = new TokenQueue();
        token.setQueueId(1);
        token.setActive(false);
        inactiveTokens.add(token);

        when(tokenQueueRepository.findByActiveFalseOrderByQueueIdAsc()).thenReturn(inactiveTokens);
        when(tokenQueueRepository.countByActiveTrue()).thenReturn(30); // Active tokens less than 50

        tokenQueueService.issueTokens();

        verify(tokenQueueRepository, times(1)).save(token);
        assertTrue(token.isActive());
        assertNotNull(token.getToken());
        assertNotNull(token.getIssuedAt());
    }

    @Test
    public void issueTokens_ActiveCountLimitReached() {
        List<TokenQueue> inactiveTokens = new ArrayList<>();
        TokenQueue token = new TokenQueue();
        token.setQueueId(1);
        token.setActive(false);
        inactiveTokens.add(token);

        when(tokenQueueRepository.findByActiveFalseOrderByQueueIdAsc()).thenReturn(inactiveTokens);
        when(tokenQueueRepository.countByActiveTrue()).thenReturn(50); // Active tokens equal to 50

        tokenQueueService.issueTokens();

        verify(tokenQueueRepository, never()).save(token);
    }

    @Test
    public void getAllTokens() {
        List<TokenQueue> tokenList = new ArrayList<>();
        when(tokenQueueRepository.findAll()).thenReturn(tokenList);

        List<TokenQueue> result = tokenQueueService.getAllTokens();

        assertEquals(tokenList, result);
        verify(tokenQueueRepository, times(1)).findAll();
    }

    @Test
    public void getToken_Success() {
        String userId = "유저1";
        TokenQueue token = new TokenQueue();
        token.setUserId(userId);
        when(tokenQueueRepository.findByUserId(userId)).thenReturn(token);

        TokenQueue result = tokenQueueService.getToken(userId);

        assertEquals(token, result);
        verify(tokenQueueRepository, times(1)).findByUserId(userId);
    }

    @Test
    void deleteToken() {
        int queueId = 1;

        tokenQueueService.deleteToken(queueId);

        verify(tokenQueueRepository, times(1)).deleteByQueueId(queueId);
    }
}