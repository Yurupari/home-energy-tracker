package com.yurupari.api_gateway.route.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GatewayRouteFactoryTest {

    @InjectMocks
    private GatewayRouteFactory routeFactory;

    @Test
    void testCreateServiceRoute() {
        var response = routeFactory.createServiceRoute(
                "TEST_SERVICE_ID",
                "/api/v1/test-service",
                "http://localhost:9999",
                "testServiceCircuitBreaker",
                "/fallback/test-service"
        );

        assertNotNull(response);
    }

    @Test
    void testCreateFallbackRoute() {
        var response = routeFactory.createFallbackRoute(
                "TEST_FALLBACK_MESSAGE",
                "/fallback/test-service"
        );

        assertNotNull(response);
    }

    @Test
    void testCreateServiceApiDocs() {
        var response = routeFactory.createServiceApiDocs(
                "TEST_SERVICE_ID",
                "/api/v3/docs",
                "http://localhost:9999"
        );

        assertNotNull(response);
    }
}