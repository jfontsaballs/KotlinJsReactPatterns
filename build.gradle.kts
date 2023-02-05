plugins {
    kotlin("js") version "1.8.10"
}

group = "com.github.jfontsaballs"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:1.0.0-pre.491"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")
}

kotlin {
    js {
        // This forces the kotlin compiler to create a .js file from code
        // If we were writing a library project for use only from other KotlinJS projectes
        // we might omit it to speed compilation up
        binaries.executable()

        // This lets Kotlin know this project targets the browser
        // Alternatively we might target Node.js
        browser {}
    }
}