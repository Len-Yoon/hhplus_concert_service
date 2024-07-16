package org.hhplus.hhplus_concert_service.business;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.Concert_item;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.persistence.Concert_item_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_seat_repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  ConcertServiceImpl implements ConcertService {

    private final Concert_repository concertRepository;
    private final Concert_item_repository concertItemRepository;
    private final Concert_seat_repository concertSeatRepository;

    @Override
    public List<Concert> checkConcert() {
        return concertRepository.findByStatus("Y");
    }

    @Override
    public List<Concert_item> checkConcertDate(int concertId) {
        return concertItemRepository.findByConcertId(concertId);
    }

    @Override
    public List<Concert_seat> checkConcertSeat(int itemId) {
        return concertSeatRepository.findAllByItemId(itemId);
    }

    @Override
    public List checkConcertByConcertDate(LocalDate checkDate) {

        List<Concert_item> concertItemList = concertItemRepository.findAll();
        List<Concert_item> newConcertItemList = new ArrayList<>();

        String checkDateStr = checkDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for(int i = 0; i < concertItemList.size(); i++){
            String date = String.valueOf(concertItemList.get(i).getConcertDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if (date.equals(checkDateStr)){
                newConcertItemList.add(concertItemList.get(i));
            }
        }
        return newConcertItemList;
    }


}
