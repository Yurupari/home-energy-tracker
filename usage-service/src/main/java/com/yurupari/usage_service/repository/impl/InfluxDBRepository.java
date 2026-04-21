package com.yurupari.usage_service.repository.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.yurupari.common_data.kafka.event.EnergyUsageEvent;
import com.yurupari.usage_service.repository.TimeSeriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class InfluxDBRepository implements TimeSeriesRepository {

    private final InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    private String influxBucket;

    @Value("${influx.org}")
    private String influxOrg;

    @Override
    public void saveUsageEnergy(EnergyUsageEvent energyUsageEvent) {
        try {
            var point = Point.measurement("energy_usage")
                    .addTag("deviceId", String.valueOf(energyUsageEvent.deviceId()))
                    .addField("energyConsumed", energyUsageEvent.energyConsumed())
                    .time(energyUsageEvent.timestamp(), WritePrecision.MS);

            influxDBClient.getWriteApiBlocking().writePoint(influxBucket, influxOrg, point);
        } catch (Exception e) {
            log.error("Failed to save into InfluxDB: energyUsageEvent={}", energyUsageEvent, e);
        }
    }

    @Override
    public List<FluxTable> getUsageEnergy(Instant from, Instant to) {
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

        return getQueryResults(fluxQuery);
    }

    @Override
    public List<FluxTable> getUsageEnergy(List<String> listDeviceId, Instant from, Instant to) {
        var deviceFilter = listDeviceId.stream()
                .map(deviceId -> String.format("r[\"deviceId\"] == \"%s\"", deviceId))
                .collect(Collectors.joining(" or "));

        var fluxQuery = String.format(
                """
                from(bucket: "%s")
                    |> range(start: time(v: "%s"), stop: time(v: "%s"))
                    |> filter(fn: (r) => r["_measurement"] == "energy_usage")
                    |> filter(fn: (r) => r["_field"] == "energyConsumed")
                    |> filter(fn: (r) => %s)
                    |> group(columns: ["deviceId"])
                    |> sum(column: "_value")
                """, influxBucket, from.toString(), to.toString(), deviceFilter);

        return getQueryResults(fluxQuery);
    }

    private List<FluxTable> getQueryResults(String fluxQuery) {
        try {
            var queryApi = influxDBClient.getQueryApi();

            return queryApi.query(fluxQuery, influxOrg);
        } catch (Exception e) {
            log.error("Failed to query InfluxDB", e);
            return List.of();
        }
    }
}
