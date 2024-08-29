// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.nexus.publish)
    alias(libs.plugins.dokka) apply false
}

val secretProps = gradleLocalProperties(rootDir, project.providers)
    .map { it.key to it.value.toString() }
    .toMap()

// Set up Sonatype repository
nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId = secretProps["sonatypeStagingProfileId"]
            username = secretProps["ossrhUsername"]
            password = secretProps["ossrhPassword"]
            // Add these lines if using new Sonatype infra
            nexusUrl = uri("https://s01.oss.sonatype.org/service/local/")
            snapshotRepositoryUrl =
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}


tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}