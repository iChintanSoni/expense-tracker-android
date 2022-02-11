val koinVersion: String by project
val kotlinxDateTimeVersion: String by project
val compileSDKVersion: String by project
val minSDKVersion: String by project
val targetSDKVersion: String by project

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = compileSDKVersion.toInt()

    defaultConfig {
        minSdk = minSDKVersion.toInt()
        targetSdk = targetSDKVersion.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation("io.insert-koin:koin-core:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion")
}