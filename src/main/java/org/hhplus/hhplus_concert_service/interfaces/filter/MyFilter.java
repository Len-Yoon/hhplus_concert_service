package org.hhplus.hhplus_concert_service.interfaces.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

    private final TokenQueueService tokenQueueService;

    public MyFilter(TokenQueueService tokenQueueService) {
        this.tokenQueueService = tokenQueueService;
    }


    //초기화 작업
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter init");
    }

    //필터 역할 수행
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info("Request URI: {}", req.getRequestURL());

        // 토큰 대기열 검증 기능 추가
        String token = req.getHeader("Authorization");
        if (token != null && !tokenQueueService.isTokenValid(token)) {
            log.info("Invalid or inactive token: {}", token);
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or inactive token");
            return;
        }

        chain.doFilter(request, response);
    }

    //기존 필터 삭제
    @Override
    public void destroy() {
        log.info("End Method checking");
        Filter.super.destroy();
    }
}
