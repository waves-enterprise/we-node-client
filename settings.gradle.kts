pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val gradleDependencyManagementVersion: String by settings
    val detektVersion: String by settings
    val ktlintVersion: String by settings
    val gitPropertiesVersion: String by settings
    val palantirGitVersion: String by settings
    val jGitVerVersion: String by settings
    val protobufPluginVersion: String by settings
    val dokkaVersion: String by settings
    val nexusStagingVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        `maven-publish`
        id("io.spring.dependency-management") version gradleDependencyManagementVersion apply false
        id("io.gitlab.arturbosch.detekt") version detektVersion apply false
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion apply false
        id("com.palantir.git-version") version palantirGitVersion apply false
        id("com.gorylenko.gradle-git-properties") version gitPropertiesVersion apply false
        id("jacoco")
        id("fr.brouillard.oss.gradle.jgitver") version jGitVerVersion
        id("com.google.protobuf") version protobufPluginVersion apply false
        id("org.jetbrains.dokka") version dokkaVersion
        id("io.codearte.nexus-staging") version nexusStagingVersion
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "we-node-client"

include(
    "we-node-client-bom",

    "we-node-client-domain",
    "we-node-client-blocking-client",
    "we-node-client-reactor-client",
    "we-node-client-coroutines-client",

    "we-node-client-http:we-node-client-ktor-client",
    "we-node-client-http:we-node-client-feign-client",

    "we-node-client-json",

    "we-node-client-grpc:we-node-client-grpc-mapper",
    "we-node-client-grpc:we-node-client-grpc-java",
    "we-node-client-grpc:we-node-client-grpc-kotlin",
    "we-node-client-grpc:we-node-client-grpc-coroutines-client",
    "we-node-client-grpc:we-node-client-grpc-blocking-client",

    "we-node-domain-test",

    "we-tx-signer:we-tx-signer-api",
    "we-tx-signer:we-tx-signer-node",
    "we-tx-signer:we-tx-signer-code",
    "we-node-client-error",
    "we-atomic",
)
