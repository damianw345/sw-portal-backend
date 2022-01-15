import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.vanniktech.dependency.graph.generator") version "0.5.0"
    id("com.github.ben-manes.versions") version "0.41.0"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "com.github.damianw345"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.springfox:springfox-swagger2:2.7.0")
    implementation("io.springfox:springfox-swagger-ui:2.7.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.hibernate.validator:hibernate-validator:6.1.5.Final")
    implementation("io.github.microutils:kotlin-logging:1.7.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks {
    register("resolveDependencies") {
        doLast {
            project.rootProject.allprojects.forEach {
                it.buildscript.configurations
                        .filter { config -> config.isCanBeResolved }
                        .forEach { config -> config.resolve() }
                it.configurations
                        .filter { config -> config.isCanBeResolved }
                        .forEach { config -> config.resolve() }
            }
        }
    }
}
