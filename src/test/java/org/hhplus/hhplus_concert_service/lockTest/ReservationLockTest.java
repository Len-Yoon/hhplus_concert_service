package org.hhplus.hhplus_concert_service.lockTest;

import org.hhplus.hhplus_concert_service.business.service.ReservationService;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.persistence.ConcertRepository;
import org.hhplus.hhplus_concert_service.persistence.ConcertSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReservationLockTest {

    private static final Logger log = LoggerFactory.getLogger(ReservationLockTest.class);

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        concertRepository.deleteAll();
        concertSeatRepository.deleteAll();

        Concert concert = new Concert();;
        concert.setStatus("Y");
        concert.setCreatedAt(LocalDateTime.now());
        concertRepository.save(concert);

        ConcertSeat concertSeat = new ConcertSeat();
        concertSeat.setStatus("Y");
        concertSeat.setItemId(1);
        concertSeat.setSeatNum(1);
        concertSeatRepository.save(concertSeat);
    }

    @Test
    @Rollback
    @DisplayName("낙관적 락 테스트")
    void testReservation_optimisticLocking() {
        Runnable task1 = () -> {
            reservationService.reservation("user1", 1, 1, 1, 100, "임시예약");
        };

        Runnable task2 = () -> {
            reservationService.reservation("user2", 1, 1, 1, 100, "임시예약");
        };

        Runnable task3 = () -> {
            reservationService.reservation("user3", 1, 1, 1, 100, "임시예약");
        };

        Runnable task4 = () -> {
            reservationService.reservation("user4", 1, 1, 1, 100, "임시예약");
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);
        Thread thread4 = new Thread(task4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            fail("Thread interrupted");
        }

        ConcertSeat concertSeat = concertSeatRepository.findBySeatId(1);
        assertEquals("예약완료", concertSeat.getStatus());
    }

    @Test
    @DisplayName("비관적 락 테스트")
    public void testReservation_pessimisticLocking() throws InterruptedException {

        Runnable task = () -> {
            try {
                reservationService.reservation("user1", 1, 1, 1, 100, "임시예약");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);
        Thread thread4 = new Thread(task);
        Thread thread5 = new Thread(task);
        Thread thread6 = new Thread(task);
        Thread thread7 = new Thread(task);
        Thread thread8 = new Thread(task);



        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();

        ConcertSeat seat = concertSeatRepository.findBySeatId(1);

        assertEquals("예약완료", seat.getStatus());
    }
}
