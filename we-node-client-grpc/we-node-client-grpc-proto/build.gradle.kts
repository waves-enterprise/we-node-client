import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

val ioGrpcVersion = "1.16.0"
val ioGrpcKotlinVersion = "1.2.1"
val protobufVersion = "3.20.1"

plugins {
    id("com.google.protobuf") version "0.8.18"
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    api(project(":we-node-client-domain"))

    api("com.google.protobuf:protobuf-java:$protobufVersion")
    api("io.grpc:grpc-stub:$ioGrpcVersion")
    api("io.grpc:grpc-netty:$ioGrpcVersion")
    api("io.grpc:grpc-protobuf:$ioGrpcVersion")

    api("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    api("io.grpc:grpc-kotlin-stub:$ioGrpcKotlinVersion")

    api("javax.annotation:javax.annotation-api")
}

val grpcJavaPlugin = "grpc"
val grpcKotlinPlugin = "grpckt"

protobuf {
    generatedFilesBaseDir = "$buildDir/generated-sources"

    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id(grpcJavaPlugin) {
            artifact = "io.grpc:protoc-gen-grpc-java:$ioGrpcVersion"
        }
        id(grpcKotlinPlugin) {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$ioGrpcKotlinVersion:jdk7@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id(grpcJavaPlugin)
                id(grpcKotlinPlugin)
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
                "$buildDir/generated-sources/main/grpc"
            )
        }
    }
}
