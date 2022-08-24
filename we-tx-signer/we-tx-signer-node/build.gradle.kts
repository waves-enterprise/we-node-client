dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-tx-signer:we-tx-signer-api"))
    api(project(":we-node-client-blocking-client"))
    api(project(":we-node-domain-test"))

    api(project(":we-node-client-http:we-node-client-feign-client"))

    val feignVersion = 11.9 // todo gradle.propertiess

    implementation("io.github.openfeign:feign-core:$feignVersion")
    implementation("io.github.openfeign:feign-jackson:$feignVersion")

    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
