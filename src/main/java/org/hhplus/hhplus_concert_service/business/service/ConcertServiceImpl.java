package org.hhplus.hhplus_concert_service.business.service;

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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class  ConcertServiceImpl implements ConcertService {

    private final Concert_repository concertRepository;
    private final Concert_item_repository concertItemRepository;
    private final Concert_seat_repository concertSeatRepository;

    @Override
    public List<Concert> checkConcert() {

        List<Concert> concertList = concertRepository.findByStatus("Y");

        if (concertList.isEmpty())
            throw new NoSuchElementException();

        return concertList;
    }

    @Override
    public List<Concert_item> checkConcertDate(int concertId) {
        List<Concert_item> concertItemList = concertItemRepository.findByConcertId(concertId);

        if (concertItemList.isEmpty())
            throw new NoSuchElementException();

        return concertItemList;
    }

    @Override
    public List<Concert_seat> checkConcertSeat(int itemId) {
        List<Concert_seat> concertSeatList = concertSeatRepository.findAllByItemId(itemId);

        if (concertSeatList.isEmpty())
            throw new NoSuchElementException();

        return concertSeatRepository.findAllByItemId(itemId);
    }

    @Override
    public List checkConcertByConcertDate(LocalDate checkDate) {

        List<Concert_item> concertItemList = concertItemRepository.findAll();
        List<Concert_item> newConcertItemList = new ArrayList<>();

        String checkDateStr = checkDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (checkDateStr.isEmpty())
            throw new IllegalArgumentException();

        for(int i = 0; i < concertItemList.size(); i++){
            String date = String.valueOf(concertItemList.get(i).getConcertDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if (date.equals(checkDateStr)){
                newConcertItemList.add(concertItemList.get(i));
            }
        }

        if (newConcertItemList.isEmpty())
            throw new NoSuchElementException();

        return newConcertItemList;
    }


}