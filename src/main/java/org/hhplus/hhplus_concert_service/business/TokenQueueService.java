package org.hhplus.hhplus_concert_service.business;

import org.hhplus.hhplus_concert_service.domain.TokenQueue;

import java.util.List;

public interface TokenQueueService {

    void generateTokenForUser(String userId);

    void issueTokens();

    List<TokenQueue> getAllTokens();

    TokenQueue getToken(String userId);

    void deleteToken(int queueId);

}
