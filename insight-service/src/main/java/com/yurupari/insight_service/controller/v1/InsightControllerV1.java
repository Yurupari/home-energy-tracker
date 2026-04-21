package com.yurupari.insight_service.controller.v1;

import com.yurupari.insight_service.model.dto.InsightDto;
import com.yurupari.insight_service.model.enums.InsightType;
import com.yurupari.insight_service.service.InsightService;
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
@RequestMapping("/api/v1/insight")
public class InsightControllerV1 {

    @Autowired
    private InsightService insightService;

    @Operation(summary = "Get saving tips for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully tips retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/saving-tips/{userId}")
    public ResponseEntity<InsightDto> getSavingTips(
            @PathVariable Long userId,
            @RequestParam Integer days
    ) {
        final var insightDto = insightService.getInsights(userId, days, InsightType.SAVING_TIPS);
        return ResponseEntity.ok(insightDto);
    }

    @Operation(summary = "Get overview for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully overview retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/overview/{userId}")
    public ResponseEntity<InsightDto> getOverview(
            @PathVariable Long userId,
            @RequestParam Integer days
    ) {
        final var insightDto = insightService.getInsights(userId, days, InsightType.OVERVIEW);
        return ResponseEntity.ok(insightDto);
    }
}
