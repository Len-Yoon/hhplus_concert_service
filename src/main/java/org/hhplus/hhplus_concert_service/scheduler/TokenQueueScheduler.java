package org.hhplus.hhplus_concert_service.scheduler;

import jakarta.servlet.http.HttpServletRequest;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TokenQueueScheduler {

    @Autowired
    private TokenQueueService tokenQueueService;

    @Scheduled(fixedRate = 10000)
    public void scheduleTokenIssuance(HttpServletRequest request) {

        tokenQueueService.issueTokens();

        String userId = request.getParameter("userId");
        if(userId.isEmpty())
            throw new IllegalArgumentException();

        List<TokenQueue> tokenQueueList = tokenQueueService.getAllTokens();

        for(int i = 0; i < tokenQueueList.size(); i++) {
            if(tokenQueueList.get(i).getUserId().equals(userId)) {
                LocalDateTime issuedAt = tokenQueueList.get(i).getIssuedAt();
                LocalDateTime now = LocalDateTime.now();

                if(now.isAfter(issuedAt.minusMinutes(10))) {
                    tokenQueueService.deleteToken(tokenQueueList.get(i).getQueueId());
                }
            }
        }
    }
}
