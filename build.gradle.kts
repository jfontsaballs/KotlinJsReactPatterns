plugins {
    kotlin("multiplatform") version "2.0.20"
}

group = "com.github.jfontsaballs"
version = "0.2"

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

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinWrappers.react)
                implementation(kotlinWrappers.reactDom)
                implementation(kotlinWrappers.emotion)
            }
        }
    }
}