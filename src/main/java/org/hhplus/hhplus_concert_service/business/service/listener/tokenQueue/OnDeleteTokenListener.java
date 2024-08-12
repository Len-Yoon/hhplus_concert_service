package org.hhplus.hhplus_concert_service.business.service.listener.tokenQueue;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.hhplus.hhplus_concert_service.business.service.event.tokenQueue.OnDeleteTokenEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnDeleteTokenListener {

    private final TokenQueueService tokenQueueService;
    private static final Logger log = LoggerFactory.getLogger(OnDeleteTokenListener.class);

    @EventListener
    public void HandleDeleteToken(OnDeleteTokenEvent event) {
        if(event == null) {
            log.error("Received null event");
            throw new IllegalArgumentException("Event cannot be null");
        }

        try {
            String userId = event.getUserId();
            int concertId = event.getConcertId();

            tokenQueueService.deleteByUserIdAndConcertId(userId, concertId);
        } catch (RuntimeException e) {
            log.error("Failed to handle DeleteToken event", e);
            throw new RuntimeException("DeleteToken processing failed", e);
        }
    }
}
