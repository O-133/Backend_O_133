package com.example.demo.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medi-Recipe API 명세서")
                        .description("영수증 분석 및 식단 추천 서비스 API입니다.")
                        .version("v1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
    }
}