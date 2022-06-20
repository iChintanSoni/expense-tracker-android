// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application")  apply (false)
    id("com.android.library") apply (false)
    id("com.google.devtools.ksp") apply (false)
    id("io.gitlab.arturbosch.detekt")
    kotlin("android") apply (false)
    jacoco
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}