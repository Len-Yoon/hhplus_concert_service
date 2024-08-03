package org.hhplus.hhplus_concert_service.interfaces;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplus_concert_service.interfaces.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MyFilter myFilter;

    @Bean
    public FilterRegistrationBean MyFilter() {
        // SpringBoot 에서는 FilterRegistrationBean을 이용해서 필터 설정(was 올릴 때 서블릿 컨테이너 올릴 때 알아서 등록을 해준다.)
        FilterRegistrationBean<MyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(myFilter); // 등록할 필터
        filterRegistrationBean.setOrder(1); // 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 필터 적용할 url 패턴

        return filterRegistrationBean;
    }

}
