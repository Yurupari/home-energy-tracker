package com.yurupari.usage_service.controller.v1;

import com.yurupari.usage_service.model.dto.UsageDto;
import com.yurupari.usage_service.service.UsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usage")
public class UsageControllerV1 {

    @Autowired
    private UsageService usageService;

    @Operation(summary = "Get usage data of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully usage data retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UsageDto> getXDaysUsageForUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "3") Integer days
    ) {
        final var usageDto = usageService.getXDaysUsageForUser(userId, days);
        return ResponseEntity.ok(usageDto);
    }
}
