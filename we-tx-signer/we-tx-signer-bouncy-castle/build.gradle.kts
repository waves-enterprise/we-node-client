dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-blocking-client"))
    api(project(":we-tx-signer:we-tx-signer-api"))
    api(project(":we-tx-signer:we-tx-signer-code"))

    implementation("org.bouncycastle:bcprov-jdk15on")
    implementation("org.bouncycastle:bcpkix-jdk15on")

    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
