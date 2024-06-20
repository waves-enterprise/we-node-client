import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf

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
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id(grpcKotlinPlugin) {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$ioGrpcKotlinVersion:jdk8@jar"
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
                "$projectDir/build//generated/source/main/grpckt",
                "$projectDir/build//generated/source/main/kotlin",
            )
        }
        proto {
            "$projectDir/../proto"
        }
    }
}

ktlint {
    filter {
        exclude {
            it.file.absolutePath.contains("${File.separator}generated${File.separator}source${File.separator}")
        }
    }
}
