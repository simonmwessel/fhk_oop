plugins {
    kotlin("jvm") version "2.1.20"
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Processing Core library when processing is installed on the system (e.g. Arch via AUR package "processing")
val processingCoreJar = fileTree("/usr/share/processing/lib/app/resources/core/library/") {
    include("core-*.jar")
}.singleFile

dependencies {
    testImplementation(kotlin("test"))
    implementation(files(processingCoreJar))
}

application {
    mainClass.set("main.kotlin.Main")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}