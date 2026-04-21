package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OllamaChatServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private OllamaChatServiceImpl ollamaChatService;

    @Mock
    private OllamaChatModel ollamaChatModel;

    @Test
    void getInsights_Success() {
        var chatResponse = mock(ChatResponse.class);
        when(ollamaChatModel.call(any(Prompt.class))).thenReturn(chatResponse);
        var generation = mock(Generation.class);
        when(chatResponse.getResult()).thenReturn(generation);
        var assistantMessage = mock(AssistantMessage.class);
        when(generation.getOutput()).thenReturn(assistantMessage);
        when(assistantMessage.getText()).thenReturn("LLM Response");

        var response = ollamaChatService.getInsights("TEST_PROMPT");

        assertNotNull(response);
    }

    @Test
    void getInsights_UnexpectedError() {
        when(ollamaChatModel.call(any(Prompt.class))).thenThrow(new RuntimeException("Unexpected error with Ollama"));

        var response = ollamaChatService.getInsights("TEST_PROMPT");

        assertNotNull(response);
    }
}