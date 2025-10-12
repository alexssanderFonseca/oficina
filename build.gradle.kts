plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
    id("io.freefair.lombok") version "8.4"
}

group = "br.com.alexsdm.postech"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("com.itextpdf:itextpdf:5.5.13.3")
    implementation("org.slf4j:slf4j-api:2.0.7")

    // MapStruct processor
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    // CRÍTICO: Adicione esta dependência para Lombok + MapStruct funcionarem juntos
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:5.5.5")
    testImplementation("com.h2database:h2")
    testImplementation("io.rest-assured:json-path:5.3.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}


tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(false)
        csv.required.set(false)
    }
}

sonar {
    properties {
        property("sonar.tests", "src/test/java")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.test.exclusions", "src/test/java/**/*.java")
        property(
            "sonar.exclusions",
            "src/main/java/**/security/**,src/main/java/**/commons/**,src/main/java/**/request/**,src/main/java/**/response/**,src/main/java/**/input/**,src/main/java/**/output/**," +
                    "src/main/java/**/exceptions/**,src/main/java/**/handler/**"
        )
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

jacoco {
    toolVersion = "0.8.12"
}