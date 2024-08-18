package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.OutboxEvent;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.OutBoxEventRepository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenQueueServiceImpl implements TokenQueueService {

    @Autowired
    private RedisTemplate<String, TokenQueue> redisTemplate;

    @Autowired
    private TokenQueueRepository tokenQueueRepository;

    @Override
    public void addTokenQueue(String userId, int concertId) {
        TokenQueue token = new TokenQueue();
        token.setUserId(userId);
        token.setToken(generateToken());
        token.setActive(false);
        token.setIssuedAt(LocalDateTime.now());
        token.setConcertId(concertId);

        long score = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(getQueueKey(String.valueOf(concertId)), token, score);
        tokenQueueRepository.save(token);
    }

    @Override
    public void activateTokens(int concertId) {
        int batchSize = 3000;
        String queueKey = getQueueKey(String.valueOf(concertId));
        Set<ZSetOperations.TypedTuple<TokenQueue>> tokens = redisTemplate.opsForZSet().rangeWithScores(queueKey, 0, batchSize - 1);

        if (tokens != null) {
            tokens.forEach(tuple -> {
                TokenQueue token = tuple.getValue();
                token.setActive(true);
                redisTemplate.opsForZSet().remove(queueKey, token);
                redisTemplate.opsForZSet().add(queueKey, token, tuple.getScore());
            });
        }
    }

    @Override
    public boolean isTokenValid(int concertId, String token) {
        Set<ZSetOperations.TypedTuple<TokenQueue>> tokens = redisTemplate.opsForZSet().rangeWithScores(getQueueKey(String.valueOf(concertId)), 0, -1);
        if (tokens != null) {
            return tokens.stream().anyMatch(tuple -> tuple.getValue().getToken().equals(token));
        }

        return false;
    }

    @Override
    public List<TokenQueue> getAllTokens(int concertId) {
        Set<ZSetOperations.TypedTuple<TokenQueue>> tokens = redisTemplate.opsForZSet().rangeWithScores(getQueueKey(String.valueOf(concertId)), 0, -1);
        return tokens != null ? tokens.stream().map(ZSetOperations.TypedTuple::getValue).collect(Collectors.toList()) : List.of();
    }

    @Override
    public TokenQueue getToken(int concertId, String userId) {
        Set<ZSetOperations.TypedTuple<TokenQueue>> tokens = redisTemplate.opsForZSet().rangeWithScores(getQueueKey(String.valueOf(concertId)), 0, -1);
        if (tokens != null) {
            return tokens.stream().map(ZSetOperations.TypedTuple::getValue)
                    .filter(t -> t.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void deleteToken(int concertId, String token) {
        Set<ZSetOperations.TypedTuple<TokenQueue>> tokens = redisTemplate.opsForZSet().rangeWithScores(getQueueKey(String.valueOf(concertId)), 0, -1);
        if (tokens != null) {
            tokens.stream().map(ZSetOperations.TypedTuple::getValue)
                    .filter(t -> t.getToken().equals(token))
                    .findFirst()
                    .ifPresent(t -> redisTemplate.opsForZSet().remove(getQueueKey(String.valueOf(concertId)), t));

            //토큰 삭제
            tokenQueueRepository.deleteByToken(token);
        }
    }

    private String getQueueKey(String concertId) {
        return "token_queue_" + concertId;
    }

    private String generateToken() {
        return "TOKEN_" + System.currentTimeMillis();
    }

}
