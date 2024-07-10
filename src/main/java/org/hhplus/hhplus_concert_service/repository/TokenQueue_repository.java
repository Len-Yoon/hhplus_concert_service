package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.TokenQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenQueue_repository extends JpaRepository<TokenQueue, Integer> {

    List<TokenQueue> findByActiveTrueOrderByQueueIdAsc();

    List<TokenQueue> findByActiveFalseOrderByQueueIdAsc();

    int countByActiveTrue();

    TokenQueue findByUserId(String userId);
}
