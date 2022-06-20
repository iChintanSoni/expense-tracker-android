pluginManagement {
    val androidGradlePluginVersion: String by settings
    val kspVersion: String by settings
    val detektVersion: String by settings
    val kotlinVersion: String by settings
    plugins {
        id("com.android.application") version (androidGradlePluginVersion) apply (false)
        id("com.android.library") version (androidGradlePluginVersion) apply (false)
        id("com.google.devtools.ksp") version (kspVersion) apply (false)
        id("io.gitlab.arturbosch.detekt") version (detektVersion)
        kotlin("android") version (kotlinVersion) apply (false)
        jacoco
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Expense Tracker"
include("app")
include("database")
include("preference")
include("domain")
include("repository")
include("common")
