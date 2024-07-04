package org.hhplus.clean_architecture.hhplus_concert_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hhplus.clean_architecture.hhplus_concert_service.entity.Payment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

    //결제 API
    @PostMapping("")
    public Payment payment(HttpServletRequest request, HttpServletResponse response) {
        int paymentId = 1;
        LocalDateTime createAt = LocalDateTime.now();
        String userId = request.getParameter("userId");
        int price = Integer.parseInt(request.getParameter("price"));


        return new Payment(paymentId, userId, price, LocalDateTime.now());
    }

}
