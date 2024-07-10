package org.hhplus.hhplus_concert_service.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.entity.TokenQueue;
import org.hhplus.hhplus_concert_service.repository.TokenQueue_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenQueueServiceImpl implements TokenQueueService {

    @Autowired
    private TokenQueue_repository tokenQueueRepository;

    @Override
    public void generateTokenForUser(String userId) {
        int activeCount = tokenQueueRepository.countByActiveTrue();
        TokenQueue token = new TokenQueue(userId);
        if(activeCount < 50){
            token.setActive(true);
            token.setToken(generateToken((token.getQueueId())));
            token.setIssuedAt(LocalDateTime.now());
        }

        tokenQueueRepository.save(token);
    }

    @Override
    public void issueTokens() {
        List<TokenQueue> inactiveTokens = tokenQueueRepository.findByActiveFalseOrderByQueueIdAsc();

        for(TokenQueue token : inactiveTokens){
            int activeCount = tokenQueueRepository.countByActiveTrue();
            if(activeCount < 50){
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
    public List<TokenQueue> getAllTokens() {
        return tokenQueueRepository.findAll();
    }

    @Override
    public TokenQueue getToken(String userId) {
        return tokenQueueRepository.findByUserId(userId);
    }

    private String generateToken(int queueId) {
        return "TOKEN_" + queueId;
    }
}
