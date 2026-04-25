package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayProperties;
import com.yurupari.api_gateway.model.ServiceConfig;
import com.yurupari.api_gateway.route.factory.GatewayRouteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.function.RouterFunction;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceRouteTest {

    private DeviceServiceRoute deviceServiceRoute;

    private GatewayProperties properties;

    @Mock
    private GatewayRouteFactory routeFactory;

    @BeforeEach
    void setUp() {
        Map<String, ServiceConfig> propertyMap = new HashMap<>();
        propertyMap.put("device-service", new ServiceConfig(
                "http://localhost:9999",
                "/api/v1/device/**",
                "deviceServiceCircuitBreaker",
                "/fallback/device",
                "Device service is down"
        ));

        properties = new GatewayProperties(propertyMap);

        deviceServiceRoute = new DeviceServiceRoute(properties, routeFactory);
    }

    @Test
    void testDeviceRoute() {
        var routerFunction = mock(RouterFunction.class);
        when(routeFactory.createServiceRoute(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(routerFunction);

        var response = deviceServiceRoute.deviceRoute();

        assertNotNull(response);
        assertEquals(routerFunction, response);
        verify(routeFactory).createServiceRoute(
                eq("device-service"),
                eq("/api/v1/device/**"),
                eq("http://localhost:9999"),
                eq("deviceServiceCircuitBreaker"),
                anyString()
        );
    }

    @Test
    void testDeviceFallbackRoute() {
        var routerFunction = mock(RouterFunction.class);
        when(routeFactory.createFallbackRoute(anyString(), anyString())).thenReturn(routerFunction);

        var response = deviceServiceRoute.deviceFallbackRoute();

        assertNotNull(response);
        assertEquals(routerFunction, response);
        verify(routeFactory).createFallbackRoute(
                anyString(),
                eq("/fallback/device")
        );
    }
}