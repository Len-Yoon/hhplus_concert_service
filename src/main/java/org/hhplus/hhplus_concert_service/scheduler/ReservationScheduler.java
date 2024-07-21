package org.hhplus.hhplus_concert_service.scheduler;

import jakarta.servlet.http.HttpServletRequest;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.domain.Reservation;
import org.hhplus.hhplus_concert_service.persistence.Concert_seat_repository;
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
    Concert_seat_repository concertSeatRepository;

    @Scheduled(fixedRate = 60000)
    public void scheduleReservationIssuance(HttpServletRequest request) {


        List<Reservation> reservationList = reservationService.checkAllReservations("임시예약");

        LocalDateTime now = LocalDateTime.now();

        for(int i = 0; i < reservationList.size(); i++) {
            LocalDateTime createdAt = reservationList.get(i).getCreatedAt();
            int seatId = reservationList.get(i).getSeatId();

            if(now.isAfter(createdAt.minusMinutes(5))) {
                concertSeatRepository.findBySeatId(seatId);

                Concert_seat concertSeat = concertSeatRepository.findBySeatId(seatId);

                concertSeat.setStatus("예약가능");

                concertSeatRepository.save(concertSeat);
            }
        }
    }
}
