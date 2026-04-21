package com.yurupari.device_service;

import com.yurupari.device_service.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.yurupari.device_service.constants.TestConstants.CREATE_DEVICE_DTO_V1_JSON;
import static com.yurupari.device_service.constants.TestConstants.UPDATE_DEVICE_DTO_V1_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = {"/sql/cleanup.sql", "/sql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DeviceServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JsonTestUtils jsonTestUtils;

	@Test
	void contextLoads() {
	}

	@Test
	void createDevice() throws Exception {
		var request = jsonTestUtils.loadRequest(CREATE_DEVICE_DTO_V1_JSON);

		mockMvc.perform(
				post("/api/v1/device/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpect(status().isCreated());
	}

	@Test
	void getDeviceById() throws Exception {
		mockMvc.perform(
				get("/api/v1/device/1"))
				.andExpect(status().isOk());
	}

	@Test
	void getDeviceById_DeviceNotFound() throws Exception {
		mockMvc.perform(
						get("/api/v1/device/9999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateDevice() throws Exception {
		var request = jsonTestUtils.loadRequest(UPDATE_DEVICE_DTO_V1_JSON);

		mockMvc.perform(
				put("/api/v1/device/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpect(status().isOk());
	}

	@Test
	void updateDevice_DeviceNotFound() throws Exception {
		var request = jsonTestUtils.loadRequest(UPDATE_DEVICE_DTO_V1_JSON);

		mockMvc.perform(
						put("/api/v1/device/9999")
								.contentType(MediaType.APPLICATION_JSON)
								.content(request))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteDevice() throws Exception {
		mockMvc.perform(
				delete("/api/v1/device/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	void deleteDevice_DeviceNotFound() throws Exception {
		mockMvc.perform(
						delete("/api/v1/device/9999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void getAllDevicesByUserId() throws Exception {
		mockMvc.perform(
				get("/api/v1/device/user/1"))
				.andExpect(status().isOk());
	}
}
