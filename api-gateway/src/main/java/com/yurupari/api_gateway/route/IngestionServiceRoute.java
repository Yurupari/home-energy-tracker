package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.yurupari.api_gateway.model.enums.GatewayService.INGESTION;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class IngestionServiceRoute {

    private final GatewayProperties properties;

    @Bean
    public RouterFunction<ServerResponse> ingestionRoute() {
        var ingestionServiceId = INGESTION.getName();
        var ingestionServiceConfig = properties.items().get(ingestionServiceId);

        return route(ingestionServiceId)
                .route(RequestPredicates.path(ingestionServiceConfig.path()), http())
                .before(uri(ingestionServiceConfig.url()))
                .build();
    }
}
