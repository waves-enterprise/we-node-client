import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

val protobufVersion: String by project
val ioGrpcVersion: String by project
val ioGrpcKotlinVersion: String by project

plugins {
    id("com.google.protobuf")
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    api(project(":we-node-client-domain"))
    api(project(":we-node-client-grpc:we-node-client-grpc-java"))

    api("com.google.protobuf:protobuf-java")
    api("com.google.protobuf:protobuf-kotlin")
    api("io.grpc:grpc-stub")
    api("io.grpc:grpc-kotlin-stub")
    api("io.grpc:grpc-netty")
    api("io.grpc:grpc-protobuf")

    api("javax.annotation:javax.annotation-api")

    protobuf(files("../proto"))
}

val grpcJavaPlugin = "grpc"
val grpcKotlinPlugin = "grpckt"

val buildDirectory = layout.buildDirectory

protobuf {
    generatedFilesBaseDir = "${buildDirectory.get().asFile.path}/generated-sources"

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id(grpcKotlinPlugin) {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$ioGrpcKotlinVersion:jdk7@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id(grpcKotlinPlugin)
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                buildDirectory.dir("generated-sources/main/kotlin").get().asFile.path,
                buildDirectory.dir("generated-sources/main/grpckt").get().asFile.path,
            )
        }
        proto {
            "$projectDir/../proto"
        }
    }
}

ktlint {
    filter {
        exclude { it.file.absolutePath.contains("${File.separator}generated-sources${File.separator}") }
    }
}
