
plugins {
    java
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.5"
    application
}

group = "com.tylerlowrey"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.tylerlowrey.MainKt")
}

repositories {
    mavenCentral()
}

val ktorVersion: String by project
val logbackVersion: String by project

dependencies {
    implementation("com.pi4j:pi4j-ktx:2.4.0") // Kotlin DSL
    implementation("com.pi4j:pi4j-core:2.3.0")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:2.3.0")
    implementation("com.pi4j:pi4j-plugin-pigpio:2.3.0")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}