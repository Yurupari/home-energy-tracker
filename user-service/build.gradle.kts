plugins {
	java
}

sourceSets {
    create("integTest") {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    }
}

configurations {
	val testConfigs = listOf("Implementation", "RuntimeOnly", "CompileOnly", "AnnotationProcessor")

	testConfigs.forEach { configSuffix ->
		val testConfig = configurations.findByName("test$configSuffix")
		val integTestConfig = configurations.findByName("integTest$configSuffix")

		if (testConfig != null && integTestConfig != null) {
			integTestConfig.extendsFrom(testConfig)
		}
	}
}

var mapstructVersion = "1.6.3"

dependencies {
	implementation(project(":common-data"))
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-flyway")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation("io.micrometer:micrometer-registry-prometheus")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:testcontainers-junit-jupiter")
	testImplementation("org.testcontainers:testcontainers-postgresql")
	testImplementation("com.h2database:h2")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

    "integTestImplementation"(sourceSets.main.get().output)
    "integTestImplementation"(sourceSets.test.get().output)
}

val integrationTest = tasks.register<Test>("integrationTest") {
    testClassesDirs = sourceSets["integTest"].output.classesDirs
    classpath = sourceSets["integTest"].runtimeClasspath
    useJUnitPlatform()

    shouldRunAfter(tasks.test)
}

tasks.check {
    dependsOn(integrationTest)
}

tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}
