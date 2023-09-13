dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-blocking-client"))
    api(project(":we-node-client-error"))
    implementation(project(":we-node-client-json"))

    api("io.github.openfeign:feign-core")
    api("io.github.openfeign:feign-jackson")
    api("io.github.openfeign:feign-slf4j")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(project(":we-node-domain-test"))
    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.github.tomakehurst:wiremock-jre8")
    testImplementation("io.mockk:mockk")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
