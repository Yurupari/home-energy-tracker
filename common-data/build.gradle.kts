plugins {
	id("java-library")
}

var mapstructVersion = "1.6.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	api("org.mapstruct:mapstruct:${mapstructVersion}")
	api("org.springframework.boot:spring-boot-starter-webmvc")
	api("com.fasterxml.jackson.core:jackson-annotations")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	api("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.bootJar {
	enabled = false
}

tasks.jar {
	enabled = true
}

tasks.test {
	filter {
		isFailOnNoMatchingTests = false
	}
}
