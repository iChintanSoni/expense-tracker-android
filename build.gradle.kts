// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("7.3.0-alpha05") apply (false)
    id("com.android.library") version ("7.3.0-alpha05") apply (false)
    id("com.google.devtools.ksp") version ("1.6.10-1.0.4") apply (false)
    kotlin("android") version ("1.6.10") apply (false)
    jacoco
}