package org.hhplus.hhplus_concert_service.business.service.listener.concert;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.business.service.event.concert.ConcertInsertEvent;
import org.hhplus.hhplus_concert_service.business.service.event.concert.ConcertItemInsertEvent;
import org.hhplus.hhplus_concert_service.business.service.event.concert.ConcertSeatInsertEvent;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.persistence.ConcertItemRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class ConcertInsertListener {

    private final ConcertRepository concertRepository;
    private final ConcertItemRepository concertItemRepository;
    private final ConcertSeatRepository concertSeatRepository;

    @Async
    @EventListener
    public void handleConcertCreatedEvent(ConcertInsertEvent event) {
        String status = event.getStatus();
        String title = event.getTitle();

        Concert concert = new Concert();
        concert.setStatus(status);
        concert.setTitle(title);
        concert.setCreatedAt(LocalDateTime.now());


        concertRepository.save(concert);
    }

    @Async
    @EventListener
    public void handleConcertItemCreatedEvent(ConcertItemInsertEvent event) {
        int concertId = event.getConcertId();
        LocalDate startDate = event.getStartDate();
        LocalDate endDate = event.getEndDate();

        int concertSize = (int) startDate.until(endDate, ChronoUnit.CENTURIES);

        for(int i = 0; i < concertSize; i++) {
            ConcertItem concertItem = new ConcertItem();

            concertItem.setConcertDate(startDate.plusDays(i));
            concertItem.setConcertId(concertId);

            concertItemRepository.save(concertItem);
        }
    }

    @Async
    @EventListener
    public void handleConcertSeatCreatedEvent(ConcertSeatInsertEvent event) {
        int itemId = event.getItemId();
        int seatSize= event.getSeatSize();
        String seatPrice = event.getSeatPrice();
        String status = event.getStatus();

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
