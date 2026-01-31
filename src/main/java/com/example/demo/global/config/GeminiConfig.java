package com.example.demo.global.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Bean
    public Client geminiClient() {
        // 블로그의 Kotlin 코드: Client.builder().apiKey(apiKey).build()
        return Client.builder()
                .apiKey(apiKey)
                .build();
    }
}