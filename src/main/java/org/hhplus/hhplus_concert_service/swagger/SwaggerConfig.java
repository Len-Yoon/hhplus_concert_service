package org.hhplus.hhplus_concert_service.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public Object OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("콘서트 예약 프로젝트 API")
                        .version("1.0.0"));
    }
}
