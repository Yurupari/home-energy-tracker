plugins {
	java
}

var springAiVersion = "2.0.0-M4"

dependencies {
	implementation(project(":common-data")) {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-data-jpa")
	}
	implementation("org.springframework.ai:spring-ai-starter-model-ollama")
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
