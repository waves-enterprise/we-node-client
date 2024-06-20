import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf

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
                "$projectDir/build/generated/source/main/java",
                "$projectDir/build/generated/source/main/grpc",
                "$projectDir/build/generated/source/main/kotlin",
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
