package com.yurupari.user_service.integration;

import com.yurupari.common_data.model.enums.Status;
import com.yurupari.user_service.repository.UserRepository;
import com.yurupari.user_service.utils.JsonTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.yurupari.user_service.constants.TestConstants.CREATE_USER_DTO_JSON;
import static com.yurupari.user_service.constants.TestConstants.UPDATE_USER_DTO_V1_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JsonTestUtils jsonTestUtils;

    @BeforeEach
    void setUp() throws Exception {
        var request = jsonTestUtils.loadRequest(CREATE_USER_DTO_JSON);

        mockMvc.perform(
                post("/api/v1/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

    @Test
    void saveUser_Success() throws Exception {
        var request = jsonTestUtils.loadRequest(CREATE_USER_DTO_JSON);

        mockMvc.perform(
                        post("/api/v1/user/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("TEST_NAME"))
                .andExpect(jsonPath("$.surname").value("TEST_SURNAME"))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.address").value("TEST_ADDRESS"))
                .andExpect(jsonPath("$.alerting").value(true))
                .andExpect(jsonPath("$.energyAlertingThreshold").value(100.0));
    }

    @Test
    void getUser_Success() throws Exception {
        mockMvc.perform(
                        get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TEST_NAME"))
                .andExpect(jsonPath("$.surname").value("TEST_SURNAME"))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.address").value("TEST_ADDRESS"))
                .andExpect(jsonPath("$.alerting").value(true))
                .andExpect(jsonPath("$.energyAlertingThreshold").value(100.0));
    }

    @Test
    void getUser_UserNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/v1/user/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_Success() throws Exception {
        var request = jsonTestUtils.loadRequest(UPDATE_USER_DTO_V1_JSON);

        mockMvc.perform(
                        put("/api/v1/user/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));

        var user = userRepository.findById(1L).orElseThrow();
        assertNotNull(user);
        assertEquals("UPDATED_TEST_NAME", user.getName());
        assertEquals("UPDATED_TEST_ADDRESS", user.getAddress());
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
    void deleteUser_Success() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/user/2"))
                .andExpect(status().isNoContent());

        var user = userRepository.findById(2L).orElseThrow();
        assertNotNull(user);
        assertEquals(Status.INACTIVE, user.getStatus());
    }

    @Test
    void deleteUser_UserNotFound() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/user/9999"))
                .andExpect(status().isNotFound());
    }
}
