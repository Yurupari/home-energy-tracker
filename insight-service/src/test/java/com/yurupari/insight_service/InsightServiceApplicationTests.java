package com.yurupari.insight_service;

import com.yurupari.insight_service.client.UsageFeignClientV1;
import com.yurupari.insight_service.model.dto.UsageDto;
import com.yurupari.insight_service.utils.JsonTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static com.yurupari.insight_service.constants.TestConstants.USAGE_DTO_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InsightServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@MockitoBean
	private UsageFeignClientV1 usageFeignClientV1;

	@MockitoBean
	private OllamaChatModel ollamaChatModel;

	@BeforeEach
	void setUp() throws IOException {
		var usageDto = jsonTestUtils.loadObject(USAGE_DTO_JSON, UsageDto.class);
		when(usageFeignClientV1.getXDaysUsageForUser(any(), any())).thenReturn(usageDto);

		var chatResponse = mock(ChatResponse.class);
		when(ollamaChatModel.call(any(Prompt.class))).thenReturn(chatResponse);
		var generation = mock(Generation.class);
		when(chatResponse.getResult()).thenReturn(generation);
		var assistantMessage = mock(AssistantMessage.class);
		when(generation.getOutput()).thenReturn(assistantMessage);
		when(assistantMessage.getText()).thenReturn("LLM Response");
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getSavingTips() throws Exception {
		mockMvc.perform(
				get("/api/v1/insight/saving-tips/1?days=3"))
				.andExpect(status().isOk());
	}

	@Test
	void getOverview() throws Exception {
		mockMvc.perform(
						get("/api/v1/insight/overview/1?days=3"))
				.andExpect(status().isOk());
	}
}
