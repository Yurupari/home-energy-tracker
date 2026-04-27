package com.yurupari.api_gateway.route;

import com.yurupari.api_gateway.config.GatewayConfigProperties;
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
class InsightServiceRouteTest {

    private InsightServiceRoute insightServiceRoute;

    @Mock
    private GatewayRouteFactory routeFactory;

    @BeforeEach
    void setUp() {
        Map<String, ServiceConfig> propertyMap = new HashMap<>();
        propertyMap.put("insight-service", new ServiceConfig(
                "http://localhost:9999",
                "/api/v1/insight/**",
                "insightServiceCircuitBreaker",
                "/fallback/insight",
                "Insight service is down",
                "/docs/insight-service/v3/api-docs"
        ));

        var properties = new GatewayConfigProperties(propertyMap);

        insightServiceRoute = new InsightServiceRoute(properties, routeFactory);
    }

    @Test
    void testInsightRoute() {
        var routerFunction = mock(RouterFunction.class);
        when(routeFactory.createServiceRoute(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(routerFunction);

        var response = insightServiceRoute.insightRoute();

        assertNotNull(response);
        assertEquals(routerFunction, response);
        verify(routeFactory).createServiceRoute(
                eq("insight-service"),
                eq("/api/v1/insight/**"),
                eq("http://localhost:9999"),
                eq("insightServiceCircuitBreaker"),
                anyString()
        );
    }

    @Test
    void testInsightFallbackRoute() {
        var routerFunction = mock(RouterFunction.class);
        when(routeFactory.createFallbackRoute(anyString(), anyString())).thenReturn(routerFunction);

        var response = insightServiceRoute.insightFallbackRoute();

        assertNotNull(response);
        assertEquals(routerFunction, response);
        verify(routeFactory).createFallbackRoute(
                anyString(),
                eq("/fallback/insight")
        );
    }

    @Test
    void testInsightServiceApiDocs() {
        var routerFunction = mock(RouterFunction.class);
        when(routeFactory.createServiceApiDocs(anyString(), anyString(), anyString())).thenReturn(routerFunction);

        var response = insightServiceRoute.insightServiceApiDocs();

        assertNotNull(response);
        assertEquals(routerFunction, response);
        verify(routeFactory).createServiceApiDocs(
                eq("insight-service"),
                anyString(),
                anyString()
        );
    }
}