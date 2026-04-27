package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayConfigProperties;
import com.yurupari.api_gateway.route.factory.GatewayRouteFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.yurupari.api_gateway.model.enums.GatewayService.INSIGHT;

@Configuration
@RequiredArgsConstructor
public class InsightServiceRoute {

    private final GatewayConfigProperties properties;
    private final GatewayRouteFactory routeFactory;

    @Bean
    public RouterFunction<ServerResponse> insightRoute() {
        var insightServiceConfig = properties.items().get(INSIGHT.getName());

        return routeFactory.createServiceRoute(
                INSIGHT.getName(),
                insightServiceConfig.path(),
                insightServiceConfig.url(),
                insightServiceConfig.circuitBreakerId(),
                insightServiceConfig.fallbackPath()
        );
    }

    @Bean
    public RouterFunction<ServerResponse> insightFallbackRoute() {
        var insightServiceConfig = properties.items().get(INSIGHT.getName());

        return routeFactory.createFallbackRoute(
                insightServiceConfig.fallbackMessage(),
                insightServiceConfig.fallbackPath()
        );
    }

    @Bean
    public RouterFunction<ServerResponse> insightServiceApiDocs() {
        var serviceId = INSIGHT.getName();
        var insightServiceConfig = properties.items().get(serviceId);

        return routeFactory.createServiceApiDocs(
                serviceId,
                insightServiceConfig.apiDocsPath(),
                insightServiceConfig.url()
        );
    }
}
