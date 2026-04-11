plugins {
	java
}

var mapstructVersion = "1.6.3"

dependencies {
	implementation(project(":common-data"))
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
