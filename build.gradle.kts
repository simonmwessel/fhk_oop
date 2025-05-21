plugins {
    kotlin("jvm") version "2.1.20"
    id("application")
    id("org.jetbrains.dokka") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Processing library jars when processing is installed on the system (e.g. Arch via AUR package "processing")
val processingLibs = fileTree("/usr/share/processing/lib/app/resources/core/library/") {
    include("core-*.jar", "gluegen-rt-*.jar", "jogl-all-*.jar")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(files(processingLibs))
}

application {
    mainClass.set("de.fhkiel.oop.Main")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
