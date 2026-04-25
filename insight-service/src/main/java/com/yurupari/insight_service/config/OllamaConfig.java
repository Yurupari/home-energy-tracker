package com.yurupari.insight_service.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class OllamaConfig {

    @Value("classpath:templates/prompts/context.txt")
    private Resource contextFile;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) throws IOException {
        var contextText = contextFile.getContentAsString(StandardCharsets.UTF_8);

        return builder
                .defaultSystem(contextText)
                .build();
    }
}
