package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Concert;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ConcertRepository extends JpaRepository<Concert, Integer> {

    //특정 콘서트 조회
    Concert findByConcertId(int concertId);
    
    //예약 가능 콘서트 조회
    List<Concert> findByStatus(String status);
}
