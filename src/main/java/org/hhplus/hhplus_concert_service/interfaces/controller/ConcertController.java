package org.hhplus.hhplus_concert_service.interfaces.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.ConcertItem;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.hhplus.hhplus_concert_service.business.service.ConcertService;
import org.hhplus.hhplus_concert_service.interfaces.controller.dto.ConcertDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("concert")
@RequiredArgsConstructor
public class ConcertController {

    private static final Logger log = LoggerFactory.getLogger(ConcertController.class);
    private final ConcertService concertService;

    //예약가능 콘서트 조회
    @GetMapping("")
    public List<Concert> checkConcert() {

        return concertService.checkConcert();
    }

    //콘서트 예약 날짜 조회
    @GetMapping("concertDate")
    public List<ConcertItem> checkConcertDate(@Valid @ModelAttribute ConcertDTO concertDTO) {
        int concertId = concertDTO.getConcertId();
        log.info("concertId ==" + concertId);

        return concertService.checkConcertDate(concertId);
    }

    //콘서트 예약 좌석 조회
    @GetMapping("concertSeat")
    public List<ConcertSeat> checkConcertSeat(@Valid @ModelAttribute ConcertDTO concertDTO) {
        int itemId = concertDTO.getItemId();

        return concertService.checkConcertSeat(itemId);
    }

    //날짜를 통한 콘서트 조회
    @GetMapping("concertByDate")
    public List<Concert> checkConcertByConcertDate(@Valid @ModelAttribute ConcertDTO concertDTO) {
        LocalDate checkDate = concertDTO.getCheckDate();

        return concertService.checkConcertByConcertDate(checkDate);
    }

    //콘서트 생성
    @PostMapping("concertInsert")
    public void concertInsert(@Valid @ModelAttribute ConcertDTO concertDTO) {
        String status = concertDTO.getStatus();
        String title = concertDTO.getTitle();

        concertService.concertInsert(status, title);
    }

    //콘서트 Item 생성
    @PostMapping("concertItemInsert")
    public void concertItemInsert(@Valid @ModelAttribute ConcertDTO concertDTO) {
        int concertId = concertDTO.getConcertId();
        LocalDate startDate = concertDTO.getStartDate();
        LocalDate endDate = concertDTO.getEndDate();

        concertService.concertItemInsert(concertId, startDate, endDate);
    }

    //콘서트 자리 생성
    @PostMapping("concertSeatInsert")
    public void concertSeatInsert(@Valid @ModelAttribute ConcertDTO concertDTO) {
        int itemId = concertDTO.getItemId();
        int seatSize = concertDTO.getSeatSize();
        String seatPrice = concertDTO.getSeatPrice();
        String status = concertDTO.getStatus();

        concertService.concertSeatInsert(itemId, seatSize, seatPrice, status);
    }

}
