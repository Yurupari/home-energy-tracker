plugins {
	id("java-library")
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.yurupari"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

tasks.bootJar {
	enabled = false
}

tasks.jar {
	enabled = true
}

repositories {
	mavenCentral()
}

var mapstructVersion = "1.6.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	api("org.springframework.boot:spring-boot-starter-web")
	api("com.fasterxml.jackson.core:jackson-annotations")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	api("org.springframework.boot:spring-boot-starter-data-jpa")
	api("org.mapstruct:mapstruct:${mapstructVersion}")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
}
