plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("io.lettuce:lettuce-core")
	implementation("com.google.guava:guava:32.1.1-jre")
	implementation("com.squareup.okhttp3:okhttp:4.11.0")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	runtimeOnly("org.postgresql:postgresql")

	compileOnly("org.projectlombok:lombok")

	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6")
	testImplementation("org.testcontainers:junit-jupiter:1.17.6")
	testImplementation("org.testcontainers:postgresql:1.17.6")
	testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.0")
	testImplementation("net.javacrumbs.json-unit:json-unit:2.31.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
