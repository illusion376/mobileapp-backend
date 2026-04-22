plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
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

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    implementation(libs.postgresql)
    implementation(libs.hikari)

    implementation(libs.ktor.serialization.kotlinx.json.jvm)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
