package com.yurupari.common_data;

import com.yurupari.common_data.config.JacksonConfig;
import com.yurupari.common_data.utils.JsonTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {

    protected JsonTestUtils jsonTestUtils;

    @BeforeEach
    protected void setUpBase() {
        var config = new JacksonConfig();
        var objectMapper = config.objectMapper();

        this.jsonTestUtils = new JsonTestUtils(objectMapper);
    }
}
