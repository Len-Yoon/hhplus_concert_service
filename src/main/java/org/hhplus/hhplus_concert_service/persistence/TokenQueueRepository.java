package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TokenQueueRepository extends JpaRepository<TokenQueue, Integer> {


    TokenQueue findByUserIdAndConcertId(String userId, int concertId);

    TokenQueue deleteByToken(String token);

    TokenQueue deleteByUserIdAndConcertId(String userId, int concertId);

}
