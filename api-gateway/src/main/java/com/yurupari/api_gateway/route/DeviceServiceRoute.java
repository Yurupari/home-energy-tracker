package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.yurupari.api_gateway.model.enums.GatewayService.DEVICE;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class DeviceServiceRoute {

    private final GatewayProperties properties;

    @Bean
    public RouterFunction<ServerResponse> deviceRoute() {
        var deviceServiceId = DEVICE.getName();
        var deviceServiceConfig = properties.items().get(deviceServiceId);

        return route(deviceServiceId)
                .route(RequestPredicates.path(deviceServiceConfig.path()), http())
                .before(uri(deviceServiceConfig.url()))
                .build();
    }
}
