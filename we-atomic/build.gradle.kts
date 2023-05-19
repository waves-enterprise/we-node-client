dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-blocking-client"))
    api(project(":we-tx-signer:we-tx-signer-api"))

    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.github.tomakehurst:wiremock-jre8")
    testImplementation("io.mockk:mockk")
    testImplementation(project(":we-node-domain-test"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
