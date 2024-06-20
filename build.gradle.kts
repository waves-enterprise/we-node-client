import io.gitlab.arturbosch.detekt.Detekt
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val kotlinCoroutinesVersion: String by project
val reactorVersion: String by project
val jacocoToolVersion: String by project
val logbackVersion: String by project
val javaxAnnotationApiVersion: String by project
val caffeineCacheVersion: String by project
val slf4jVersion: String by project
val bouncycastleVersion: String by project

val ioGrpcVersion: String by project
val ioGrpcKotlinVersion: String by project
val protobufVersion: String by project
val reactorBomVersion: String by project

val junitVersion: String by project
val hamcrestVersion: String by project
val mockkVersion: String by project
val wireMockVersion: String by project
val kotestVersion: String by project

val ktorVersion: String by project

val weMavenUser: String? by project
val weMavenPassword: String? by project

val sonaTypeMavenUser: String? by project
val sonaTypeMavenPassword: String? by project

val weMavenBasePath = "https://artifacts.wavesenterprise.com/repository/"

val sonaTypeBasePath = "https://s01.oss.sonatype.org"
val gitHubProject: String by project
val githubUrl: String by project

val feignVersion: String by project
val jacksonVersion: String by project

plugins {
    kotlin("jvm") apply false
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin")
    id("io.spring.dependency-management") apply false
    id("io.gitlab.arturbosch.detekt") apply false
    id("org.jlleitschuh.gradle.ktlint") apply false
    id("com.palantir.git-version") apply false
    id("com.gorylenko.gradle-git-properties") apply false
    id("fr.brouillard.oss.gradle.jgitver")
    id("org.jetbrains.dokka")
    id("jacoco")
}

if (sonaTypeMavenUser != null && sonaTypeMavenUser != null) {
    nexusPublishing {
        repositories {
            sonatype {
                nexusUrl.set(uri("$sonaTypeBasePath/service/local/"))
                snapshotRepositoryUrl.set(uri("$sonaTypeBasePath/content/repositories/snapshots/"))
                username.set(sonaTypeMavenUser)
                password.set(sonaTypeMavenPassword)
            }
        }
    }
}


jgitver {
    strategy = fr.brouillard.oss.jgitver.Strategies.PATTERN
    versionPattern =
        "\${M}.\${m}.\${meta.COMMIT_DISTANCE}-\${meta.GIT_SHA1_8}\${-~meta.QUALIFIED_BRANCH_NAME}-SNAPSHOT"
    nonQualifierBranches = "master,dev,main"
}

allprojects {
    group = "com.wavesenterprise"
    version = "-" // set by jgitver

    repositories {
        mavenCentral()
        maven {
            name = "maven-snapshots"
            url = uri("https://artifacts.wavesenterprise.com/repository/maven-snapshots/")
            mavenContent {
                snapshotsOnly()
            }
            credentials {
                username = weMavenUser
                password = weMavenPassword
            }
        }

        maven {
            name = "maven-releases"
            url = uri("https://artifacts.wavesenterprise.com/repository/maven-releases/")
            mavenContent {
                releasesOnly()
            }
            credentials {
                username = weMavenUser
                password = weMavenPassword
            }
        }
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "maven-publish")

    publishing {
        repositories {
            if (weMavenUser != null && weMavenPassword != null) {
                maven {
                    name = "WE-artifacts"
                    afterEvaluate {
                        url = uri(
                            "$weMavenBasePath${
                                if (project.version.toString()
                                        .endsWith("-SNAPSHOT")
                                ) "maven-snapshots" else "maven-releases"
                            }"
                        )
                    }
                    credentials {
                        username = weMavenUser
                        password = weMavenPassword
                    }
                }
            }

            if (sonaTypeMavenPassword != null && sonaTypeMavenUser != null) {
                maven {
                    name = "SonaType-maven-central-staging"
                    val releasesUrl = uri("$sonaTypeBasePath/service/local/staging/deploy/maven2/")
                    afterEvaluate {
                        url = if (version.toString()
                                .endsWith("SNAPSHOT")
                        ) throw kotlin.Exception("shouldn't publish snapshot") else releasesUrl
                    }
                    credentials {
                        username = sonaTypeMavenUser
                        password = sonaTypeMavenPassword
                    }
                }
            }
        }
    }
}

configure(
    subprojects.filter { it.name != "we-node-client-bom" }
) {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "signing")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "jacoco")
    apply(plugin = "org.jetbrains.dokka")

    val jacocoCoverageFile = layout.buildDirectory.dir("jacocoReports/test/jacocoTestReport.xml").get().asFile

    tasks.withType<JacocoReport> {
        reports {
            xml.apply {
                required.set(true)
                outputLocation.set(jacocoCoverageFile)
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

    val sourcesJar by tasks.creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles sources JAR"
        archiveClassifier.set("sources")
        from(project.the<SourceSetContainer>()["main"].allSource)
    }

    val dokkaJavadoc by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)
    val javadocJar by tasks.creating(Jar::class) {
        dependsOn(dokkaJavadoc)
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles javadoc JAR"
        archiveClassifier.set("javadoc")
        from(dokkaJavadoc.outputDirectory)
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                versionMapping {
                    allVariants {
                        fromResolutionResult()
                    }
                }
                afterEvaluate {
                    artifact(sourcesJar)
                    artifact(javadocJar)
                }
                pom {
                    packaging = "jar"
                    name.set(project.name)
                    url.set(githubUrl + gitHubProject)
                    description.set("WE Node Client for Java/Kotlin")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    scm {
                        connection.set("scm:$githubUrl$gitHubProject")
                        developerConnection.set("scm:git@github.com:$gitHubProject.git")
                        url.set(githubUrl + gitHubProject)
                    }

                    developers {
                        developer {
                            id.set("kt3")
                            name.set("Stepan Kashintsev")
                            email.set("kpote3@gmail.com")
                        }
                        developer {
                            id.set("bekirev")
                            name.set("Artem Bekirev")
                            email.set("abekirev@gmail.com")
                        }
                        developer {
                            id.set("donyfutura")
                            name.set("Daniil Georgiev")
                            email.set("donyfutura@gmail.com")
                        }
                    }
                }
            }
        }
    }

    signing {
        afterEvaluate {
            if (!project.version.toString().endsWith("SNAPSHOT")) {
                sign(publishing.publications["mavenJava"])
            }
        }
    }

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion")
            mavenBom("org.jetbrains.kotlinx:kotlinx-coroutines-bom:$kotlinCoroutinesVersion")
            mavenBom("com.fasterxml.jackson:jackson-bom:$jacksonVersion")
            mavenBom("com.google.protobuf:protobuf-bom:$protobufVersion")
            mavenBom("io.grpc:grpc-bom:$ioGrpcVersion")
            mavenBom("io.github.openfeign:feign-bom:$feignVersion")
            mavenBom("io.ktor:ktor-bom:$ktorVersion")
            mavenBom("io.projectreactor:reactor-bom:$reactorBomVersion")

            mavenBom("org.junit:junit-bom:$junitVersion")
        }
        dependencies {
            dependency("io.grpc:grpc-kotlin-stub:$ioGrpcKotlinVersion")
            dependency("javax.annotation:javax.annotation-api:$javaxAnnotationApiVersion")

            dependency("ch.qos.logback:logback-classic:$logbackVersion")
            dependency("org.slf4j:slf4j-api:$slf4jVersion")
            dependency("com.github.ben-manes.caffeine:caffeine:$caffeineCacheVersion")

            dependency("org.bouncycastle:bcprov-jdk18on:$bouncycastleVersion")
            dependency("org.bouncycastle:bcpkix-jdk18on:$bouncycastleVersion")

            dependency("io.mockk:mockk:$mockkVersion")

            dependency("org.hamcrest:hamcrest:$hamcrestVersion")
            dependency("org.hamcrest:hamcrest-core:$hamcrestVersion")
            dependency("org.hamcrest:hamcrest-library:$hamcrestVersion")

            dependency("org.wiremock:wiremock:$wireMockVersion")
            dependency("io.kotest:kotest-runner-junit5:$kotestVersion")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    jacoco {
        toolVersion = jacocoToolVersion
        reportsDirectory.set(layout.buildDirectory.dir("jacocoReports").get().asFile)
    }
}
