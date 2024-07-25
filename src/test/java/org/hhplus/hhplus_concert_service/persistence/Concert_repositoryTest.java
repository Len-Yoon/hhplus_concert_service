package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.Concert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Concert_repositoryTest {

    @Autowired
    private ConcertRepository concertRepository;

    @Test
    public void setUp() {
        // 테스트 데이터 초기화
        Concert concert1 = new Concert();
        concert1.setConcertId(1);
        concert1.setStatus("Y");
        concert1.setTitle("Concert1");
        concert1.setCreatedAt(LocalDateTime.now());
        concertRepository.save(concert1);

        Concert concert2 = new Concert();
        concert2.setConcertId(2);
        concert2.setStatus("N");
        concert2.setTitle("Concert2");
        concert2.setCreatedAt(LocalDateTime.now());
        concertRepository.save(concert2);

        Concert concert3 = new Concert();
        concert3.setConcertId(3);
        concert3.setStatus("Y");
        concert3.setTitle("Concert3");
        concert3.setCreatedAt(LocalDateTime.now());
        concertRepository.save(concert3);
    }

    @Test
    public void testFindByConcertId() {
        // 특정 콘서트를 ID로 조회
        Concert concert = concertRepository.findByConcertId(1);
        assertNotNull(concert);
        assertEquals(1, concert.getConcertId());
        assertEquals("Y", concert.getStatus());
    }

    @Test
    public void testFindByStatus() {
        // 상태로 콘서트 조회
        List<Concert> availableConcerts = concertRepository.findByStatus("Y");
        assertNotNull(availableConcerts);
        assertEquals(2, availableConcerts.size());
    }
}