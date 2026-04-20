plugins {
	java
	application
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
}

// Use build directory outside OneDrive to avoid file locking issues
layout.buildDirectory = file("C:/Temp/gradle-build/java-bootcamp")

group = "com.wcc.bootcamp.java"
version = "0.0.1-SNAPSHOT"
description = "Java Bootcamp "

application {
	mainClass.set("com.wcc.bootcamp.java.mentorship.MentorshipWebApplication")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
	sourceSets {
		main {
			java {
				srcDirs("src/main/java", "participants/victoria/project/src/main/java")
			}
			resources {
				srcDirs("src/main/resources", "participants/victoria/project/src/main/resources")
			}
		}
		test {
			java {
				srcDirs("src/test/java", "participants/victoria/project/src/test/java")
			}
			resources {
				srcDirs("src/test/resources", "participants/victoria/project/src/test/resources")
			}
		}
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Copy> {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
