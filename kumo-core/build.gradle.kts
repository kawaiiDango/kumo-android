import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    `java-library`
    `maven-publish`
    signing
    alias(libs.plugins.dokka)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(libs.rtree)
    implementation(libs.kumo.api)

    testImplementation(libs.junit)
    testImplementation(libs.slf4j.api)
    testImplementation(libs.jsoup)
}

group = libs.versions.publish.group.id.get()
version = libs.versions.publish.version.get()

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = libs.versions.publish.group.id.get()
                artifactId = libs.versions.publish.artifact.id.get()
                version = libs.versions.publish.version.get()

                from(components["java"])

                pom {
                    name = libs.versions.publish.artifact.id.get()
                    description = "Android port of Kumo, a word cloud generator."
                    url = "https://github.com/kawaiiDango/kumo-android"
                    licenses {
                        license {
                            name = "MIT"
                            url = "https://www.mit.edu/~amini/LICENSE.md"
                        }
                    }
                    developers {
                        developer {
                            id = "kawaiiDango"
                            name = "Kawaii Dango"
                        }
                    }
                    scm {
                        connection = "scm:git:github.com:kawaiiDango/kumo-android.git"
                        developerConnection =
                            "scm:git:ssh://github.com:kawaiiDango/kumo-android.git"
                        url = "https://github.com/kawaiiDango/kumo-android"
                    }
                }
            }
        }
    }
}

signing {
    val secretProps = gradleLocalProperties(rootDir, project.providers)
        .map { it.key to it.value.toString() }
        .toMap()

    useInMemoryPgpKeys(
        secretProps["signing.keyId"],
        secretProps["signing.key"],
        secretProps["signing.password"]
    )
    sign(publishing.publications)
}