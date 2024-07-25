package org.hhplus.hhplus_concert_service.scheduler;

import jakarta.servlet.http.HttpServletRequest;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
import org.hhplus.hhplus_concert_service.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationScheduler {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ConcertSeatRepository concertSeatRepository;

    @Scheduled(fixedRate = 60000)
    public void scheduleReservationIssuance(HttpServletRequest request) {


        List<Reservation> reservationList = reservationService.checkAllReservations("T");

        LocalDateTime now = LocalDateTime.now();

        for(int i = 0; i < reservationList.size(); i++) {
            LocalDateTime createdAt = reservationList.get(i).getCreatedAt();
            int seatId = reservationList.get(i).getSeatId();

            if(now.isAfter(createdAt.minusMinutes(5))) {
                concertSeatRepository.findBySeatId(seatId);

                ConcertSeat concertSeat = concertSeatRepository.findBySeatId(seatId);

                concertSeat.setStatus("Y");

                concertSeatRepository.save(concertSeat);
            }
        }
    }
}
