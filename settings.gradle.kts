rootProject.name = "KotlinJsReactPatterns"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("kotlinWrappers") {
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:0.0.1-pre.815")
        }
    }
}