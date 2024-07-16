package org.hhplus.hhplus_concert_service.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.Utils;
import org.hhplus.hhplus_concert_service.domain.Concert;
import org.hhplus.hhplus_concert_service.domain.Concert_item;
import org.hhplus.hhplus_concert_service.domain.Concert_seat;
import org.hhplus.hhplus_concert_service.business.ConcertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("concert")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    //예약가능 콘서트 조회
    @GetMapping("")
    public List<Concert> checkConccert(HttpServletRequest request, HttpServletResponse response) {

        return concertService.checkConcert();
    }

    //콘서트 예약 날짜 조회
    @GetMapping("concertDate")
    public List<Concert_item> checkConcertDate(HttpServletRequest request, HttpServletResponse response) {
        int concertId = Utils.checkNullByInt(request.getParameter("concertId"));

        return concertService.checkConcertDate(concertId);
    }

    //콘서트 예약 좌석 조회
    @GetMapping("concertSeat")
    public List<Concert_seat> checkConcertSeat(HttpServletRequest request, HttpServletResponse response) {
        int concertItemId = Utils.checkNullByInt("concertItemId");

        return concertService.checkConcertSeat(concertItemId);
    }

    //날짜를 통한 콘서트 조회
    @GetMapping("concertByDate")
    public List<Concert> checkConcertByConcertDate(HttpServletRequest request, HttpServletResponse response) {
        LocalDate checkDate = LocalDate.parse(request.getParameter("checkDate"));

        return concertService.checkConcertByConcertDate(checkDate);
    }

}
