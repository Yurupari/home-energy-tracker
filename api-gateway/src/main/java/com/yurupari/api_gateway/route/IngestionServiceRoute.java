package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayProperties;
import com.yurupari.api_gateway.route.factory.GatewayRouteFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.yurupari.api_gateway.model.enums.GatewayService.INGESTION;

@Configuration
@RequiredArgsConstructor
public class IngestionServiceRoute {

    private final GatewayProperties properties;
    private final GatewayRouteFactory routeFactory;

    @Bean
    public RouterFunction<ServerResponse> ingestionRoute() {
        var ingestionServiceConfig = properties.items().get(INGESTION.getName());

        return routeFactory.createServiceRoute(
                INGESTION.getName(),
                ingestionServiceConfig.path(),
                ingestionServiceConfig.url(),
                ingestionServiceConfig.circuitBreakerId(),
                ingestionServiceConfig.fallbackPath()
        );
    }

    @Bean
    public RouterFunction<ServerResponse> ingestionFallbackRoute() {
        var ingestionServiceConfig = properties.items().get(INGESTION.getName());

        return routeFactory.createFallbackRoute(
                ingestionServiceConfig.fallbackMessage(),
                ingestionServiceConfig.fallbackPath()
        );
    }
}
