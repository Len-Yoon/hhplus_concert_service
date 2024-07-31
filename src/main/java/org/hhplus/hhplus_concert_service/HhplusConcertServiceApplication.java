package org.hhplus.hhplus_concert_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@ServletComponentScan
@EnableCaching
@SpringBootApplication
public class HhplusConcertServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(HhplusConcertServiceApplication.class, args);
    }
}