package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class Concert_seat_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(Concert_seat_repositoryTest.class);

    @Autowired
    ConcertSeatRepository concertSeatRepository;

    @Test
    void findAllByItemId() {
    }

    @Test
    void findBySeatId() {
        ConcertSeat concertSeat = concertSeatRepository.findBySeatId(1);
    }
}