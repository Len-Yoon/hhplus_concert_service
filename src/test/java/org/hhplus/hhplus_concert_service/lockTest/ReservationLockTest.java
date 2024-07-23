package org.hhplus.hhplus_concert_service.lockTest;

import jakarta.persistence.OptimisticLockException;
import org.hhplus.hhplus_concert_service.business.constans.ReservationConstants;
import org.hhplus.hhplus_concert_service.business.service.ReservationService;
import org.hhplus.hhplus_concert_service.business.service.ReservationServiceImpl;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.persistence.Concert_repository;
import org.hhplus.hhplus_concert_service.persistence.Concert_seat_repository;
import org.hhplus.hhplus_concert_service.persistence.Reservation_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationLockTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private Reservation_repository reservationRepository;

    @Autowired
    private Concert_repository concertRepository;

    @Autowired
    private Concert_seat_repository concertSeatRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        Concert concert = new Concert();
        concert.setConcertId(1);
        concert.setStatus("Y");
        concertRepository.save(concert);

        Concert_seat concertSeat = new Concert_seat();
        concertSeat.setSeatId(1);
        concertSeat.setStatus("예약가능");
        concertSeatRepository.save(concertSeat);
    }

    @Test
    public void testOptimisticLocking() throws InterruptedException, ExecutionException {

        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executorService.submit(new ReservationTask()));
        }

        int optimisticLockingFailureCount = 0;
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                if (e.getCause() instanceof OptimisticLockException) {
                    optimisticLockingFailureCount++;
                }
            }
        }

        executorService.shutdown();

        assertTrue(optimisticLockingFailureCount > 0);
    }

    private class ReservationTask implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            try {
                ReservationServiceImpl reservationService = new ReservationServiceImpl(concertRepository, concertSeatRepository, reservationRepository);

                reservationService.reservation("user" + Thread.currentThread().getId(), 1, 1, 1, 1000, "예약완료");
            } catch (OptimisticLockException e) {

                throw e;
            }
            return null;
        }
    }


}
