package org.hhplus.hhplus_concert_service.business.service;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.persistence.ConcertItemRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class  ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertItemRepository concertItemRepository;
    private final ConcertSeatRepository concertSeatRepository;

    @Override
    public List<Concert> checkConcert() {

        List<Concert> concertList = concertRepository.findByStatus("Y");

        if (concertList.isEmpty())
            throw new NoSuchElementException();

        return concertList;
    }

    @Override
    public List<ConcertItem> checkConcertDate(int concertId) {
        List<ConcertItem> concertItemList = concertItemRepository.findByConcertId(concertId);

        if (concertItemList.isEmpty()) {
            throw new NoSuchElementException();
        }

        return concertItemList;
    }

    @Override
    public List<ConcertSeat> checkConcertSeat(int itemId) {
        List<ConcertSeat> concertSeatList = concertSeatRepository.findAllByItemId(itemId);

        if (concertSeatList.isEmpty())
            throw new NoSuchElementException();

        return concertSeatList;
    }

    @Override
    public List checkConcertByConcertDate(LocalDate checkDate) {

        List<ConcertItem> concertItemList = concertItemRepository.findAll();
        List<ConcertItem> newConcertItemList = new ArrayList<>();

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

    @Override
    public void concertInsert(String status, String title) {

        Concert concert = new Concert();
        concert.setStatus(status);
        concert.setTitle(title);
        concert.setCreatedAt(LocalDateTime.now());


        concertRepository.save(concert);
    }

    @Override
    public void concertItemInsert(int concertId, LocalDate startDate, LocalDate endDate) {
        int concertSize = (int) startDate.until(endDate, ChronoUnit.CENTURIES);

        for(int i = 0; i < concertSize; i++) {
            ConcertItem concertItem = new ConcertItem();

            concertItem.setConcertDate(startDate.plusDays(i));
            concertItem.setConcertId(concertId);

            concertItemRepository.save(concertItem);
        }
    }

    @Override
    public void concertSeatInsert(int itemId, int seatSize, String seatPrice, String status) {

        String[] seatPriceList = seatPrice.split(",");

        for(int i = 0; i < seatSize; i++) {
            ConcertSeat concertSeat = new ConcertSeat();

            concertSeat.setSeatNum(i+1);
            concertSeat.setSeatPrice(Integer.parseInt(seatPriceList[i]));
            concertSeat.setStatus(status);
            concertSeat.setItemId(itemId);

            concertSeatRepository.save(concertSeat);
        }
    }


}
