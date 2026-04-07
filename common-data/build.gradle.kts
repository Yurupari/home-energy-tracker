plugins {
	id("java-library")
}

tasks.bootJar {
	enabled = false
}

tasks.jar {
	enabled = true
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	api("org.springframework.boot:spring-boot-starter-web")
	api("com.fasterxml.jackson.core:jackson-annotations")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	api("org.springframework.boot:spring-boot-starter-data-jpa")
}
