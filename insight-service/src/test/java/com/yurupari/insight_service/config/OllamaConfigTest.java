package com.yurupari.insight_service.config;

import com.yurupari.insight_service.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OllamaConfigTest extends BaseUnitTest {

    private OllamaConfig ollamaConfig;

    @BeforeEach
    void setUp() {
        ollamaConfig = new OllamaConfig();

        String context = "You are an expert energy efficiency advisor.";
        Resource resource = new ByteArrayResource(context.getBytes());

        ReflectionTestUtils.setField(ollamaConfig, "contextFile", resource);
    }

    @Test
    void chatClient() throws IOException {
        var builder = mock(ChatClient.Builder.class);

        when(builder.defaultSystem(anyString())).thenReturn(builder);

        var expectedClient = mock(ChatClient.class);
        when(builder.build()).thenReturn(expectedClient);

        var response = ollamaConfig.chatClient(builder);

        assertNotNull(response);
        verify(builder).defaultSystem("You are an expert energy efficiency advisor.");
    }
}