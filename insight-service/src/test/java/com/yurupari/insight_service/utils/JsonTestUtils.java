package com.yurupari.insight_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JsonTestUtils {

    private final ObjectMapper objectMapper;

    public String loadRequest(String filePath) throws IOException {
        var resource = new ClassPathResource(filePath);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public <T> T loadObject(String resourcePath, Class<T> targetClass) throws IOException {
        var resource = new ClassPathResource(resourcePath);
        return objectMapper.readValue(resource.getInputStream(), targetClass);
    }
}
