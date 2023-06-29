dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-blocking-client"))
    api(project(":we-node-client-grpc:we-node-client-grpc-java"))
    api(project(":we-node-client-error"))
    implementation(project(":we-node-client-grpc:we-node-client-grpc-mapper"))
    implementation("io.grpc:grpc-core")
    implementation("org.slf4j:slf4j-api")

    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
