package org.hhplus.hhplus_concert_service.interfaces.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MyFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

    //초기화 작업
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TestFilter init");
    }

    //필터 역할 수행
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("Request URI: {}", req.getRequestURL());

        chain.doFilter(request, response);

        log.info("Return URI: {}", req.getRequestURL());
    }

    //기존 필터 삭제
    @Override
    public void destroy() {
        log.info("End Method checking");
        Filter.super.destroy();
    }
}
