package com.team5.earchive.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI earchiveOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EArchive Backend API")
                        .version("v1")
                        .description(
                                "API hệ thống lưu trữ điện tử. "
                                + "Hiện đã triển khai endpoint health; "
                                + "các API nghiệp vụ sẽ được bổ sung."
                        ));
    }
}