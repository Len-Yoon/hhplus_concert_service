package org.hhplus.hhplus_concert_service.scheduler;

import org.hhplus.hhplus_concert_service.service.TokenQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenQueueScheduler {

    @Autowired
    private TokenQueueService tokenQueueService;

    @Scheduled(fixedRate = 10000)
    public void scheduleTokenIssuance() {
        tokenQueueService.issueTokens();
    }

}
