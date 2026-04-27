import org.apache.tools.ant.filters.ReplaceTokens

plugins {
	java
}

version = "0.0.1-SNAPSHOT"

tasks.processResources {
	filesMatching("**/application.yaml") {
		filter(
			mapOf("tokens" to mapOf("version" to project.version.toString())),
			ReplaceTokens::class.java
		)
	}
}

var mapstructVersion = "1.6.3"

dependencies {
	implementation(project(":common-data")) {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-data-jpa")
	}
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-kafka")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-kafka-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
}

tasks.bootJar {
	archiveFileName.set("${project.name}.jar")
}
