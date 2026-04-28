plugins {
	java
}

var springAiVersion = "2.0.0-M4"
var springCloudVersion = "2025.1.1"
var openFeignVersion = "4.3.2"

dependencies {
	implementation(project(":common-data")) {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-data-jpa")
	}
	implementation("org.springframework.ai:spring-ai-starter-model-ollama")
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:${openFeignVersion}")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
	implementation("io.github.resilience4j:resilience4j-spring-boot3")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${springAiVersion}")
	}
}

tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}
