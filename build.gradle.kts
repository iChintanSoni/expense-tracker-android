// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("7.2.0-alpha07") apply (false)
    id("com.android.library") version ("7.2.0-alpha07") apply (false)
    kotlin("android") version ("1.6.10") apply (false)
}

//tasks.register("clean").configure{
//    delete(rootProject.buildDir)
//}

//val customDokkaTask by creating(DokkaTask::class) {
//    dependencies {
//        plugins("org.jetbrains.dokka:kotlin-as-java-plugin:1.6.10")
//    }
//}