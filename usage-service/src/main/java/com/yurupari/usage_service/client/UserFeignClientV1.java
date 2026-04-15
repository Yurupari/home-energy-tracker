package com.yurupari.usage_service.client;

import com.yurupari.usage_service.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "${services.user-service.url}",
        path = "/api/v1/user"
)
public interface UserFeignClientV1 {

    @GetMapping("/{userId}")
    UserDto getUserById(@PathVariable("userId") Long id);
}
