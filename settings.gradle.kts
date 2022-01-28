pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
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
rootProject.buildFileName = "build.gradle.kts"
include("app")
include("database")
include("preference")
include("domain")
include("repository")
