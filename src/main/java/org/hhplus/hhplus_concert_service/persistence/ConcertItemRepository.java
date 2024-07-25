package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ConcertItemRepository extends JpaRepository<ConcertItem, Integer> {

    //특정 콘서트 상세 정보 조회
    ConcertItem findByItemId(int itemId);
    List<ConcertItem> findByConcertId(int concertId);

    //날짜를 통한 콘서트 조회
    List<ConcertItem> findByConcertDate(LocalDate concertDate);
}
