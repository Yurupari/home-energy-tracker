package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayConfigProperties;
import com.yurupari.api_gateway.route.factory.GatewayRouteFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.yurupari.api_gateway.model.enums.GatewayService.DEVICE;

@Configuration
@RequiredArgsConstructor
public class DeviceServiceRoute {

    private final GatewayConfigProperties properties;
    private final GatewayRouteFactory routeFactory;

    @Bean
    public RouterFunction<ServerResponse> deviceRoute() {
        var deviceServiceConfig = properties.items().get(DEVICE.getName());

        return routeFactory.createServiceRoute(
                DEVICE.getName(),
                deviceServiceConfig.path(),
                deviceServiceConfig.url(),
                deviceServiceConfig.circuitBreakerId(),
                deviceServiceConfig.fallbackPath()
        );
    }

    @Bean
    public RouterFunction<ServerResponse> deviceFallbackRoute() {
        var deviceServiceConfig = properties.items().get(DEVICE.getName());

        return routeFactory.createFallbackRoute(
                deviceServiceConfig.fallbackMessage(),
                deviceServiceConfig.fallbackPath()
        );
    }
}
