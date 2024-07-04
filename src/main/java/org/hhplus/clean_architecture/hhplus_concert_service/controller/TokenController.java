package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.clean_architecture.hhplus_concert_service.dto.token.check;
import org.hhplus.clean_architecture.hhplus_concert_service.dto.token.issuance;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {

    //토큰 발급 API
    @PostMapping("issuance")
    public issuance issuance(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");

        int tokenId = 1;
        int watingNum = 10000;
        String status = "테스트";

        return new issuance(userId, tokenId, watingNum, status);
    }

    //유저 토큰 조회 API(대기열 정보 확인)
    @PostMapping("check")
    public check check(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        int tokenId = 1;
        int watingNum = 100;
        String  status = "테스트";

        return new check(userId, tokenId, watingNum, status);
    }

    //토큰 무효화 API
    @PostMapping("delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        int tokenId = Integer.parseInt(request.getParameter("tokenId"));

        String message = "완료";
        return message;
    }
}
