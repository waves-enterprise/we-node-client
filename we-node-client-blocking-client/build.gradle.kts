dependencies {
    implementation(kotlin("stdlib"))
    implementation("ch.qos.logback:logback-classic")
    implementation("com.github.ben-manes.caffeine:caffeine")

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-error"))

    implementation(project(":we-node-client-json"))

    testImplementation(project(":we-node-domain-test"))
    testImplementation("ch.qos.logback:logback-classic")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.hamcrest:hamcrest-library")
    testImplementation("io.mockk:mockk")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
