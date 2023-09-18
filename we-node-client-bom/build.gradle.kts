val gitHubProject: String by project
val githubUrl: String by project

plugins {
    `maven-publish`
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        project.rootProject.subprojects.forEach { project ->
            api(project)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("we-node-client-bom") {
            from(components["javaPlatform"])

            pom {
                packaging = "pom"
                name.set(project.name)
                url.set(githubUrl + gitHubProject)
                description.set("WE Node Client BOM")

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
                }
            }
        }
    }
}
