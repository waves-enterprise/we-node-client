dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-grpc:we-node-client-grpc-java"))

    testImplementation("io.mockk:mockk")
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
