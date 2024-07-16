package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenQueue_repository extends JpaRepository<TokenQueue, Integer> {

    List<TokenQueue> findByActiveTrueOrderByQueueIdAsc();

    List<TokenQueue> findByActiveFalseOrderByQueueIdAsc();

    int countByActiveTrue();

    TokenQueue findByUserId(String userId);

    TokenQueue deleteByQueueId(int queueId);
}
