package com.yurupari.insight_service.service.impl;

import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.insight_service.client.UsageFeignClientV1;
import com.yurupari.insight_service.model.dto.UsageDto;
import com.yurupari.insight_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class UsageServiceImpl implements UsageService {

    private final UsageFeignClientV1 usageFeignClientV1;

    @Override
    public UsageDto getXDaysUsageForUser(Long userId, int days) {
        return usageFeignClientV1.getXDaysUsageForUser(userId, days);
    }
}
