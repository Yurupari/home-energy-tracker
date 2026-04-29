package com.yurupari.user_service.support;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.postgresql.PostgreSQLContainer;

public abstract class PostgreSQLTestcontainerBase {

    @ServiceConnection
    @Container
    static PostgreSQLContainer postgresql = new PostgreSQLContainer("postgres:18-alpine")
            .withDatabaseName("home_energy_tracker")
            .withUsername("root")
            .withPassword("password");
}
