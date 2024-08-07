plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "Itstime"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// swagger
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// s3
	implementation("io.awspring.cloud:spring-cloud-aws-s3:3.1.1")

	// logback
	implementation("org.springframework.boot:spring-boot-starter-logging")

	// actuator & prometheus
	implementation ("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly ("io.micrometer:micrometer-registry-prometheus")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
