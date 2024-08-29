plugins {
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":kumo-api"))
    implementation(libs.language.en)
    implementation(libs.language.zh)

    testImplementation(libs.slf4j.api)
    testImplementation(libs.junit)
}