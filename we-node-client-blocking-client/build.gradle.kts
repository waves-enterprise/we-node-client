dependencies {
    implementation(kotlin("stdlib"))
    implementation("ch.qos.logback:logback-classic")
    implementation("io.github.openfeign:feign-core")

    api(project(":we-node-client-domain"))

    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.github.tomakehurst:wiremock-jre8")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
