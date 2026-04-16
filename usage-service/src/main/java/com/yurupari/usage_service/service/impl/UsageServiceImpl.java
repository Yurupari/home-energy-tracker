package com.yurupari.usage_service.service.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.yurupari.common_data.annotation.Loggable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.model.DeviceEnergy;
import com.yurupari.usage_service.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Loggable
public class UsageServiceImpl implements UsageService {

    private final InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    private String influxBucket;

    @Value("${influx.org}")
    private String influxOrg;

    @Override
    public void energyUsageEvent(EnergyUsageEvent energyUsageEvent) {
        var point = Point.measurement("energy_usage")
                .addTag("deviceId", String.valueOf(energyUsageEvent.deviceId()))
                .addField("energyConsumed", energyUsageEvent.energyConsumed())
                .time(energyUsageEvent.timestamp(), WritePrecision.MS);

        influxDBClient.getWriteApiBlocking().writePoint(influxBucket, influxOrg, point);
    }

    @Override
    public List<DeviceEnergy> getUsageEnergy(Instant from, Instant to) {
        var fluxTables = getFluxTables(from, to);

        List<DeviceEnergy> deviceEnergies = new ArrayList<>();

        for (var table : fluxTables) {
            for (var record : table.getRecords()) {
                var deviceId = (String) record.getValueByKey("deviceId");
                var energyConsumed = record.getValueByKey("_value") instanceof Number ?
                        ((Number) record.getValueByKey("_value")).doubleValue() : 0.0;

                deviceEnergies.add(
                        DeviceEnergy.builder()
                                .deviceId(Long.valueOf(deviceId))
                                .energyConsumed(energyConsumed)
                                .build()
                );
            }
        }

        return deviceEnergies;
    }

    private List<FluxTable> getFluxTables(Instant from, Instant to) {
        var fluxQuery = String.format(
                """
                from(bucket: "%s")
                |> range(start: time(v: "%s"), stop: time(v: "%s"))
                |> filter(fn: (r) => r["_measurement"] == "energy_usage")
                |> filter(fn: (r) => r["_field"] == "energyConsumed")
                |> group(columns: ["deviceId"])
                |> sum(column: "_value")
                """, influxBucket, from.toString(), to
        );

        var queryApi = influxDBClient.getQueryApi();

        return queryApi.query(fluxQuery, influxOrg);
    }
}
