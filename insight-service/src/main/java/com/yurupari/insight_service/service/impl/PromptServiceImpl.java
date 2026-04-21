package com.yurupari.insight_service.service.impl;

import com.yurupari.insight_service.exception.PromptLoadException;
import com.yurupari.insight_service.model.PromptProperties;
import com.yurupari.insight_service.model.enums.InsightType;
import com.yurupari.insight_service.service.PromptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromptServiceImpl implements PromptService {

    private final PromptProperties promptProperties;

    @Override
    public String getPromptTemplate(InsightType insightType) {
        var fileName = promptProperties.files().get(insightType.getValue());
        var filePath = promptProperties.basePath() + fileName;

        try {
            log.info("Loading prompt template: insightType={}", insightType);
            var resource = new ClassPathResource(filePath);

            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PromptLoadException(insightType);
        }
    }
}
