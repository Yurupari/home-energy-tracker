package com.yurupari.insight_service.client;

import com.yurupari.insight_service.model.dto.UsageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "user-service",
        url = "${services.usage-service.url}",
        path = "/api/v1/usage"
)
public interface UsageFeignClientV1 {

    @GetMapping("/{userId}")
    UsageDto getXDaysUsageForUser(@PathVariable("userId") Long userId, @RequestParam Integer days);
}
