# AGENTS.md — Guidance for automated coding/operational agents

Purpose: Give AI agents the minimal, actionable knowledge to build, run, and inspect the Home Energy Tracker microservices.

Quick plan for agents
- Bring up infra (PostgreSQL, Kafka, InfluxDB, Mailpit, Kafka UI)
- Build or run a single service locally
- Exercise the ingestion → usage → alert pipeline and verify side-effects

Quick start (infra)
- From repo root: `docker-compose -f docker-compose-local.yaml up -d` (uses `docker-compose-local.yaml`)
- Stop: `docker compose down`
- If DB issues occur: remove/recreate volumes or re-run `docker/postgres/init.sql`

Build / run a service
- Build: `./gradlew build`
- Build each service `./gradlew :<service>:build`

Architecture & key components
- Microservices (top-level dirs): `alert-service`, `api-gateway`, `device-service`, `ingestion-service`, `insight-service`, `usage-service`, `user-service`.
- Stack: **Spring Boot 4** + **Java 25** for all services; **`insight-service`** uses **Spring AI** (Ollama). **Spring Cloud 2025.1.1** on the gateway (Gateway Server WebMVC, Resilience4j circuit breakers). No Spring Cloud Config Server in this repo—config is per-service `application.yaml`.
- Important infra files: `docker-compose.yaml`, `docker-compose-local.yaml`, `docker/postgres/init.sql`, `docker/kafka_data/`, `influxdb_data/`.
- Human-oriented architecture diagrams: `diagrams/*.png` (see top-level `README.md`).

Critical integration points & dataflows (explicit)
- Ingestion → Kafka → Usage → InfluxDB & Alerts → Alerting consumer
    - Topic `energy-usage`: produced by `ingestion-service` (`ingestion-service/src/main/java/com/yurupari/ingestion_service/service/IngestionService.java`) and consumed by `usage-service` (`usage-service/src/main/java/com/yurupari/usage_service/service/UsageService.java`).
    - Topic `energy-alerts`: produced by `usage-service` (aggregation/threshold logic) and consumed by `alert-service` (`alert-service/src/main/java/com/yurupari/alert_service/service/AlertService.java`).
- InfluxDB usage: `usage-service/src/main/java/com/yurupari/usage_service/config/InfluxDBConfig.java` and writes/queries in `UsageService.java`.
- PostgreSQL: DB name `home_energy_tracker`, init in `docker/postgres/init.sql`; JDBC URLs appear in services' `src/main/resources/application.yaml`.

API Gateway (routing & security)
- Gateway port **9000**; routes under `/api/v1/...` proxy to localhost backends with **circuit breakers** (see `api-gateway/src/main/java/com/yurupari/api_gateway/route/*.java`).

Observability & useful endpoints
- Kafka UI: http://localhost:8070 (inspect topics and messages)
- Mailpit (SMTP/web): SMTP **1025**, web UI http://localhost:8025 (outgoing email from `alert-service`)
- InfluxDB (UI/API): http://localhost:8072 (org/bucket/token from `docker-compose.yaml` env vars, e.g. bucket `usage-bucket`)
- Service ports (defaults in `application.yaml`):
    - `user-service` **8080**, `device-service` **8081**, `ingestion-service` **8082**, `usage-service` **8083**, `alert-service` **8084**, `insight-service` **8085**, `api-gateway` **9000**

Example agent actions (curl + checks)
- Post a test event (Kafka traffic; **direct to ingestion**, no JWT):
    - `curl -X POST http://localhost:8082/api/v1/ingestion -H 'Content-Type: application/json' -d '{"deviceId":"dev-1","timestamp":"2026-01-01T12:00:00Z","energyConsumed":1200}'`
- Through the **gateway** (requires JWT): `http://localhost:9000/api/v1/ingestion`.
- Verify `usage-service` consumed and wrote to InfluxDB: check `usage-service` logs and query the Influx bucket via HTTP API; also check scheduled aggregation logs (scheduler in `UsageService.java`).
- Force alert: craft a high `watts` payload and confirm Mailpit received an email (web UI at **8025**).

Agent runbook checks (short)
- Confirm ports reachable: **3306** (PostgreSQL), **9094** (Kafka external), **8072** (Influx), **8025** (Mailpit), **8070** (Kafka UI)
- Confirm Kafka topics exist and show message flow (Kafka UI or service logs)
- Confirm Influx writes by querying the bucket referenced in `docker-compose.yaml` envs
- Check service logs: `docker compose logs <container>` or run the JAR locally and capture stdout

Project-specific conventions
- Gradle wrapper present in root folder — prefer `./gradlew`.
- Package names use underscores: e.g. `com.yurupari.ingestion_service`.
- Kafka bootstrap server for **host-based** service runs: **`localhost:9094`** (external advertised listener in `docker-compose.yaml` or `docker-compose-local.yaml`). Services' `application.yaml` should use this when not on the Docker network.
- JSON type mapping for Kafka consumers is configured in service properties (look for `spring.kafka.consumer.yaml.spring.json.type.mapping`).

Files to reference when automating (examples)
- `docker-compose.yaml` — infra and envs
- `docker-compose-local.yaml` — infra and envs
- `docker/postgres/init.sql` — DB bootstrap
- `ingestion-service/src/main/java/com/yurupari/ingestion_service/controller/IngestionController.java`
- `ingestion-service/src/main/java/com/yurupari/ingestion_service/service/IngestionService.java`
- `usage-service/src/main/java/com/yurupari/usage_service/service/UsageService.java`
- `usage-service/src/main/java/com/yurupari/usage_service/config/InfluxDBConfig.java`
- `alert-service/src/main/java/com/yurupari/alert_service/service/AlertService.java`
- `api-gateway/src/main/java/com/yurupari/api_gateway/route/UserServiceRoutes.java` (and sibling route classes)

Notes
- Java version: use **JDK 25** (declared in each module build.gradle.kts).
- The top-level `README.md`.

End of AGENTS.md