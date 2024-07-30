package org.hhplus.hhplus_concert_service.business.service;

import org.hhplus.hhplus_concert_service.domain.TokenQueue;

import java.util.List;
import java.util.Optional;

public interface TokenQueueService {

    void generateTokenForUser(String userId);

    void issueTokens();

    List<TokenQueue> getAllTokens();

    TokenQueue getToken(String userId);

    void deleteToken(int queueId);

    boolean isTokenValid(String token);

}
