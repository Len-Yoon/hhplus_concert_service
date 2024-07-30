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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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



}
