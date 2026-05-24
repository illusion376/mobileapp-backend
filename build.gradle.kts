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
    implementation(libs.sse)
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.2")
    implementation("com.google.firebase:firebase-admin:9.2.0")
    implementation("jakarta.mail:jakarta.mail-api:2.1.2")
    implementation("org.eclipse.angus:jakarta.mail:1.1.0")
    implementation("io.ktor:ktor-client-core:2.3.10")
    implementation("io.ktor:ktor-client-cio:2.3.10")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.10")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.10")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.3.10")
    implementation(libs.koin.ktor)
    implementation(libs.koin.ktor.logger)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    implementation(libs.postgresql)
    implementation(libs.hikari)

    implementation(libs.ktor.serialization.kotlinx.json.jvm)

    implementation(libs.jbcrypt)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
