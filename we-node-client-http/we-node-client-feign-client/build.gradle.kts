dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-blocking-client"))
    api(project(":we-node-client-error"))
    implementation(project(":we-node-client-json"))

    implementation("io.github.openfeign:feign-core")
    implementation("io.github.openfeign:feign-jackson")
    implementation("io.github.openfeign:feign-slf4j")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.github.tomakehurst:wiremock-jre8")
    testImplementation("io.mockk:mockk")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
