// java-bootcamp/participants/fugitiva/ASCII_mirror/build.gradle.kts

plugins {
    id("java")
}

group = "com.wcc.bootcamp.java"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:4.0.2") // Specify version explicitly
}
