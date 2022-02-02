import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.vanniktech.dependency.graph.generator") version "0.7.0"
    id("com.github.ben-manes.versions") version "0.41.0"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "com.github.damianw345"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val graphQLVersion = "12.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.4")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("io.github.microutils:kotlin-logging:2.1.21")

    // GraphQL
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:$graphQLVersion")
    testImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter-test:$graphQLVersion")

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
