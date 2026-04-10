package com.yurupari.ingestion_service.controller.v1;

import com.yurupari.common_data.annotation.TrackTime;
import com.yurupari.ingestion_service.model.dto.EnergyUsageDto;
import com.yurupari.ingestion_service.service.IngestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingestion")
@TrackTime
public class IngestionControllerV1 {

    @Autowired
    private IngestionService ingestionService;

    @Operation(summary = "Ingest the energy usage data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully ingested the energy usage data")
    })
    @PostMapping
    public ResponseEntity<Void> ingestData(@RequestBody EnergyUsageDto energyUsageDto) {
        ingestionService.ingestEnergyUsage(energyUsageDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
