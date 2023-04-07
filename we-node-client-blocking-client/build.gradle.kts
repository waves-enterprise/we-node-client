dependencies {
    implementation(kotlin("stdlib"))
    implementation("ch.qos.logback:logback-classic")
    implementation("com.github.ben-manes.caffeine:caffeine")

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-error"))

    implementation(project(":we-node-client-json"))

    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.hamcrest:hamcrest-library")
    testImplementation("io.mockk:mockk")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
