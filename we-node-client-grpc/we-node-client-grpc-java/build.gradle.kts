import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

val protobufVersion: String by project
val ioGrpcVersion: String by project
val ioGrpcKotlinVersion: String by project
val osxArch: String? by project

plugins {
    id("com.google.protobuf")
}

dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-domain"))

    api("com.google.protobuf:protobuf-java")
    api("com.google.protobuf:protobuf-kotlin")
    api("io.grpc:grpc-stub")
    api("io.grpc:grpc-netty")
    api("io.grpc:grpc-protobuf")

    api("javax.annotation:javax.annotation-api")

    protobuf(files("../proto"))
}

val grpcJavaPlugin = "grpc"
val buildDirectory = layout.buildDirectory

protobuf {
    generatedFilesBaseDir = "${buildDirectory.get().asFile.path}/generated-sources"

    val archPostFix = if (osxArch == "m1") ":osx-x86_64" else ""

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion$archPostFix"
    }

    plugins {
        id(grpcJavaPlugin) {
            artifact = "io.grpc:protoc-gen-grpc-java:$ioGrpcVersion$archPostFix"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id(grpcJavaPlugin)
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                buildDirectory.dir("generated-sources/main/java").get().asFile.path,
                buildDirectory.dir("generated-sources/main/grpc").get().asFile.path,
                buildDirectory.dir("generated-sources/main/kotlin").get().asFile.path,
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
