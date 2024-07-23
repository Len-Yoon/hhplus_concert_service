package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TokenQueue_repository extends JpaRepository<TokenQueue, Integer> {

    List<TokenQueue> findByActiveTrueOrderByQueueIdAsc();

    List<TokenQueue> findByActiveFalseOrderByQueueIdAsc();

    int countByActiveTrue();

    TokenQueue findByUserId(String userId);

    TokenQueue deleteByQueueId(int queueId);

    boolean findByToken(String token);
}
