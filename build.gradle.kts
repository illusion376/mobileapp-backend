val ktorVersion: String by project

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

group = "io.illusion"
version = "0.0.1"

application {
    mainClass = "io.illusion.ApplicationKt"
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cio)
    implementation(libs.logback.classic)
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.2")

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
