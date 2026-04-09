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
	api("org.springframework.boot:spring-boot-starter-webmvc")
	api("com.fasterxml.jackson.core:jackson-annotations")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	api("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
