package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.Concert_item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface Concert_item_repository extends JpaRepository<Concert_item, Integer> {

    //특정 콘서트 상세 정보 조회
    Concert_item findByItemId(int itemId);
    List<Concert_item> findByConcertId(int concertId);

    //날짜를 통한 콘서트 조회
    List<Concert_item> findByConcertDate(LocalDate concertDate);
}
