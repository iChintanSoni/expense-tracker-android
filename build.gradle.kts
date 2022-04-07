// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("7.3.0-alpha07") apply (false)
    id("com.android.library") version ("7.3.0-alpha07") apply (false)
    id("com.google.devtools.ksp") version ("1.6.10-1.0.4") apply (false)
    id("io.gitlab.arturbosch.detekt") version "1.20.0-RC2"
    kotlin("android") version ("1.6.10") apply (false)
    jacoco
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}