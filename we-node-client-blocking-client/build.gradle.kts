dependencies {
    implementation(kotlin("stdlib"))
    implementation("ch.qos.logback:logback-classic")

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-error"))

    implementation(project(":we-node-client-http:we-node-client-http-dto"))

    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.github.tomakehurst:wiremock-jre8")
    testImplementation("io.mockk:mockk")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
