package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenQueueServiceImpl implements TokenQueueService {

    @Autowired
    private TokenQueueRepository tokenQueueRepository;

    private static final int MAX_ACTIVE_TOKENS = 50;

    @Override
    public void generateTokenForUser(String userId) {
        int activeCount = tokenQueueRepository.countByActiveTrue();
        TokenQueue token = new TokenQueue();
        if(activeCount < MAX_ACTIVE_TOKENS){
            token.setActive(true);
            token.setToken(generateToken((token.getQueueId())));
            token.setIssuedAt(LocalDateTime.now());
        } else {
            token.setActive(false);
        }

        tokenQueueRepository.save(token);
    }

    @Override
    public void issueTokens() {
        List<TokenQueue> inactiveTokens = tokenQueueRepository.findByActiveFalseOrderByQueueIdAsc();

        for(TokenQueue token : inactiveTokens){
            int activeCount = tokenQueueRepository.countByActiveTrue();
            if(activeCount < MAX_ACTIVE_TOKENS){
                token.setActive(true);
                token.setToken(generateToken((token.getQueueId())));
                token.setIssuedAt(LocalDateTime.now());
                tokenQueueRepository.save(token);
            } else {
                break;
            }

        }
    }

    @Override
    public boolean isTokenValid(String token) {

        return tokenQueueRepository.findByToken(token);
    }

    @Override
    public List<TokenQueue> getAllTokens() {
        return tokenQueueRepository.findAll();
    }

    @Override
    public TokenQueue getToken(String userId) {
        return tokenQueueRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(int queueId) {
        tokenQueueRepository.deleteByQueueId(queueId);
    }

    private String generateToken(int queueId) {
        return "TOKEN_" + queueId;
    }
}
