package com.yurupari.user_service;

import com.yurupari.user_service.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.yurupari.user_service.constants.TestConstants.CREATE_USER_DTO_JSON;
import static com.yurupari.user_service.constants.TestConstants.UPDATE_USER_DTO_V1_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@Test
	void contextLoads() {
	}

	@Test
	void createUser() throws Exception {
		var request = jsonTestUtils.loadRequest(CREATE_USER_DTO_JSON);

		mockMvc.perform(
				post("/api/v1/user/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpect(status().isCreated());
	}

	@Test
	void getUserById() throws Exception {
		mockMvc.perform(
				get("/api/v1/user/1")
		).andExpect(status().isOk());
	}

	@Test
	void getUserById_UserNotFound() throws Exception {
		mockMvc.perform(
				get("/api/v1/user/9999")
		).andExpect(status().isNotFound());
	}

	@Test
	void updateUser() throws Exception {
		var request = jsonTestUtils.loadRequest(UPDATE_USER_DTO_V1_JSON);

		mockMvc.perform(
				put("/api/v1/user/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpect(status().isOk());
	}

	@Test
	void updateUser_UserNotFound() throws Exception {
		var request = jsonTestUtils.loadRequest(UPDATE_USER_DTO_V1_JSON);

		mockMvc.perform(
						put("/api/v1/user/9999")
								.contentType(MediaType.APPLICATION_JSON)
								.content(request))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteUser() throws Exception {
		mockMvc.perform(
				delete("/api/v1/user/1")
		).andExpect(status().isNoContent());
	}

	@Test
	void deleteUser_UserNotFound() throws Exception {
		mockMvc.perform(
				delete("/api/v1/user/9999")
		).andExpect(status().isNotFound());
	}
}
