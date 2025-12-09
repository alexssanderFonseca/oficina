dependencies {
    implementation(project(":ordem_servico"))
    implementation(project(":orcamento"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:5.5.5")
    testImplementation("com.h2database:h2")
    testImplementation("io.rest-assured:json-path:5.3.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
