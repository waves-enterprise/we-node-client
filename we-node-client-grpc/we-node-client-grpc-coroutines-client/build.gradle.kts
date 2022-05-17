dependencies {
    implementation(kotlin("stdlib"))

    api(project(":we-node-client-coroutines-client"))
    implementation(project(":we-node-client-grpc:we-node-client-grpc-kotlin"))
    implementation(project(":we-node-client-grpc:we-node-client-grpc-mapper"))
    implementation("io.grpc:grpc-core")
}
