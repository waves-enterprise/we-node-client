import fr.brouillard.oss.gradle.plugins.JGitverPluginExtension
import fr.brouillard.oss.jgitver.Strategies.MAVEN
import io.gitlab.arturbosch.detekt.Detekt
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val kotlinCoroutinesVersion: String by project
val springBootVersion: String by project
val springCloudVersion: String by project
val jacocoToolVersion: String by project
val logbackVersion: String by project

val junitPlatformLauncherVersion: String by project
val mockkVersion: String by project
val springMockkVersion: String by project

val ktorVersion: String by project

plugins {
    kotlin("jvm") apply false
    `maven-publish`
    kotlin("plugin.spring") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    id("io.gitlab.arturbosch.detekt") apply false
    id("org.jlleitschuh.gradle.ktlint") apply false
    id("com.palantir.git-version") apply false
    id("com.gorylenko.gradle-git-properties") apply false
    id("fr.brouillard.oss.gradle.jgitver")
    id("jacoco")
}

configure<JGitverPluginExtension> {
    strategy = MAVEN
    nonQualifierBranches = "master,dev"
}

allprojects {
    group = "com.wavesplatform.we.sdk.node.client"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "jacoco")

    val jacocoCoverageFile = "$buildDir/jacocoReports/test/jacocoTestReport.xml"

    tasks.withType<JacocoReport> {
        reports {
            xml.apply {
                required.set(true)
                outputLocation.set(file(jacocoCoverageFile))
            }
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events = setOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED
            )
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
        finalizedBy("jacocoTestReport")
    }

    val detektConfigFilePath = "$rootDir/gradle/detekt-config.yml"

    tasks.withType<Detekt> {
        exclude("resources/")
        exclude("build/")
        config.setFrom(detektConfigFilePath)
        buildUponDefaultConfig = true
    }

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion") {
                bomProperty("kotlin.version", kotlinVersion)
            }
        }
        dependencies {
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinCoroutinesVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinCoroutinesVersion")

            dependency("ch.qos.logback:logback-classic:$logbackVersion")

            dependency("io.ktor:ktor-client-core:$ktorVersion")
            dependency("io.ktor:ktor-client-cio:$ktorVersion")
            dependency("io.ktor:ktor-client-logging:$ktorVersion")
            dependency("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            dependency("io.ktor:ktor-serialization-jackson:$ktorVersion")

            dependency("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")
            dependency("io.mockk:mockk:$mockkVersion")
            dependency("com.ninja-squad:springmockk:$springMockkVersion")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    jacoco {
        toolVersion = jacocoToolVersion
        reportsDirectory.set(file("$buildDir/jacocoReports"))
    }
}
