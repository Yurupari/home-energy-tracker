package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.service.LLMChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OllamaChatServiceImpl implements LLMChatService {

    private final OllamaChatModel ollamaChatModel;

    @Override
    public String getInsights(String prompt) {
        try {
            var chatResponse = ollamaChatModel.call(
                    Prompt.builder()
                            .content(prompt)
                            .build()
            );

            return Optional.ofNullable(chatResponse.getResult())
                    .map(r -> r.getOutput().getText())
                    .orElse("Couldn't find any tips now. Try later");
        } catch (Exception e) {
            log.error("Unexpected error calling Ollama", e);
            return "There was an error finding tips. Try later";
        }
    }
}
