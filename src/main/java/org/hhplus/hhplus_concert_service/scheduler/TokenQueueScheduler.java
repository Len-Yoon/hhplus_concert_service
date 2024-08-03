package org.hhplus.hhplus_concert_service.scheduler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenQueueScheduler {

    @Autowired
    private TokenQueueService tokenQueueService;

    private final ConcertRepository concertRepository;
    private final TokenQueueRepository tokenQueueRepository;

    @Scheduled(fixedRate = 10000)
    public void activateTokensInQueue() {

        List<Concert> concertIdList = concertRepository.findByStatus("Y");

        for (int i = 0; i < concertIdList.size(); i++) {
            int concertId = concertIdList.get(i).getConcertId();

            tokenQueueService.activateTokens(concertId);
        }
    }

    @Scheduled(fixedRate = 1800000)
    public void deleteTokens() {
        List<Concert> concertIdList = concertRepository.findByStatus("Y");

        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < concertIdList.size(); i++) {
            int concertId = concertIdList.get(i).getConcertId();

            List<TokenQueue> tokenQueueList = tokenQueueService.getAllTokens(concertId);

            for(int j = 0; j < tokenQueueList.size(); j++) {
                LocalDateTime tokenIssuedAt = tokenQueueList.get(i).getIssuedAt();
                String token = tokenQueueList.get(i).getToken();

                if(now.isAfter(tokenIssuedAt.minusMinutes(30))) {
                    tokenQueueService.deleteToken(concertId, token);
                }
            }
        }
    }
//
//    @Scheduled(fixedRate = 10000)
//    public void scheduleTokenIssuance(HttpServletRequest request) {
//
//        tokenQueueService.issueTokens();
//
//        String userId = request.getParameter("userId");
//        if(userId.isEmpty())
//            throw new IllegalArgumentException();
//
//        List<TokenQueue> tokenQueueList = tokenQueueService.getAllTokens();
//
//        for(int i = 0; i < tokenQueueList.size(); i++) {
//            if(tokenQueueList.get(i).getUserId().equals(userId)) {
//                LocalDateTime issuedAt = tokenQueueList.get(i).getIssuedAt();
//                LocalDateTime now = LocalDateTime.now();
//
//                if(now.isAfter(issuedAt.minusMinutes(10))) {
//                    tokenQueueService.deleteToken(tokenQueueList.get(i).getQueueId());
//                }
//            }
//        }
//    }


}
