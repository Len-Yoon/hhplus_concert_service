package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {

    //토큰 발급 API
    @PostMapping("issuance")
    public void issuance() {

    }

    //유저 토큰 조회 API(대기열 정보 확인)
    @PostMapping("check")
    public void check() {

    }

    //토큰 무효화 API
    @PostMapping("delete")
    public void delete() {

    }
}
