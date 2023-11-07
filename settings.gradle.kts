pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        resolutionStrategy {
            eachPlugin {
                if (requested.id.id == "kotlinx-serialization") {
                    useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
                }
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")


    }
}

rootProject.name = "CDGNews"
include(":app")
