val koinVersion: String by project
val compileSDKVersion: String by project
val minSDKVersion: String by project
val targetSDKVersion: String by project
val coroutinesVersion: String by project

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkPreview = compileSDKVersion

    defaultConfig {
        minSdk = minSDKVersion.toInt()
        targetSdk = targetSDKVersion.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":preference"))
    implementation(project(":database"))
    implementation(project(":domain"))
    implementation(project(":common"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    androidTestImplementation("io.insert-koin:koin-test:$koinVersion")
    androidTestImplementation("io.insert-koin:koin-android:$koinVersion")
}