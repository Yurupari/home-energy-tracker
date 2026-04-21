package com.yurupari.insight_service.config;

import com.yurupari.insight_service.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OllamaConfigTest extends BaseUnitTest {

    @InjectMocks
    private OllamaConfig ollamaConfig;

    @Test
    void chatClient() {
        var builder = mock(ChatClient.Builder.class);

        when(builder.defaultSystem(anyString())).thenReturn(builder);

        var expectedClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(expectedClient);

        var response = ollamaConfig.chatClient(builder);

        assertNotNull(response);
        verify(builder).defaultSystem(contains("expert energy efficiency advisor"));
    }
}