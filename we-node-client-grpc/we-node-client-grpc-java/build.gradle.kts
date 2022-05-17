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

protobuf {
    generatedFilesBaseDir = "$buildDir/generated-sources"

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id(grpcJavaPlugin) {
            artifact = "io.grpc:protoc-gen-grpc-java:$ioGrpcVersion"
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
                "$buildDir/generated-sources/main/java",
                "$buildDir/generated-sources/main/grpc",
                "$buildDir/generated-sources/main/kotlin",
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
