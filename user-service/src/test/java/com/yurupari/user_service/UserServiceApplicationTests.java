package com.yurupari.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yurupari.user_service.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = {"/sql/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@TestConfiguration
	static class TestConfig {
		@Bean
		public ObjectMapper objectMapper() {
			return new ObjectMapper();
		}
	}

	@Test
	void contextLoads() {
	}

	@Test
	void createUser() throws Exception {
		var request = jsonTestUtils.loadRequest("model/dto/create_user_dto_v1.json");

		mockMvc.perform(
				post("/api/v1/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpect(status().isCreated());
	}
}
