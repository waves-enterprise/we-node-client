dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-tx-signer:we-tx-signer-api"))
    api(project(":we-node-client-blocking-client"))
    api(project(":we-node-domain-test"))

    api(project(":we-node-client-http:we-node-client-feign-client"))

    implementation("io.github.openfeign:feign-core")
    implementation("io.github.openfeign:feign-jackson")

    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
