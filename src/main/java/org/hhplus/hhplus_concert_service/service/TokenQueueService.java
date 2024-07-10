package org.hhplus.hhplus_concert_service.service;

import org.hhplus.hhplus_concert_service.entity.TokenQueue;

import java.util.List;

public interface TokenQueueService {

    void generateTokenForUser(String userId);

    void issueTokens();

    List<TokenQueue> getAllTokens();

}
