plugins {
	java
}

sourceSets {
    create("integTest") {
        java {
            compileClasspath += main.get().output + test.get().output
            runtimeClasspath += main.get().output + test.get().output
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
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

	"integTestImplementation"(sourceSets.main.get().output)
	"integTestImplementation"(sourceSets.test.get().output)
}

configurations {
	getByName("integTestImplementation") {
		extendsFrom(configurations.testImplementation.get())
	}
	getByName("integTestRuntimeOnly") {
		extendsFrom(configurations.testRuntimeOnly.get())
	}
	getByName("integTestCompileOnly") {
		extendsFrom(configurations.testCompileOnly.get())
	}
	getByName("integTestAnnotationProcessor") {
		extendsFrom(configurations.testAnnotationProcessor.get())
	}
}

tasks.register<Test>("integTest") {
	description = "Run integration tests"
	group = "verification"
	testClassesDirs = sourceSets.getByName("integTest").output.classesDirs
	classpath = sourceSets.getByName("integTest").runtimeClasspath
}

tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}
