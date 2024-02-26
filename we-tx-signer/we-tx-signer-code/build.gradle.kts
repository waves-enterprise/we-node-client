val bouncycastleVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-blocking-client"))
    api(project(":we-tx-signer:we-tx-signer-api"))
    api(project(":we-node-client-http:we-node-client-feign-client"))
    implementation(project(":we-node-client-json"))

    implementation("org.bouncycastle:bcprov-jdk15on:$bouncycastleVersion")
    implementation("org.bouncycastle:bcpkix-jdk15on:$bouncycastleVersion")

    implementation("ru.CryptoPro:ades-core")
    implementation("ru.CryptoPro:cades")
    implementation("ru.CryptoPro:jcp")
    implementation("ru.CryptoPro:jcp-rev-check")
    implementation("ru.CryptoPro:jcsp")

    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
