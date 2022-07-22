val koinVersion: String by project
val composeVersion: String by project
val kotlinxDateTimeVersion: String by project
val compileSDKVersion: String by project
val minSDKVersion: String by project
val targetSDKVersion: String by project
val detektVersion: String by project

plugins {
    id("com.android.application")
    id("io.gitlab.arturbosch.detekt")
    kotlin("android")
}

android {
    compileSdk = compileSDKVersion.toInt()

    defaultConfig {
        applicationId = "dev.chintansoni.expensetracker"
        minSdk = minSDKVersion.toInt()
        targetSdk = targetSDKVersion.toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    namespace = "dev.chintansoni.expensetracker"

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
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes.plus("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0-alpha05")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha01")
    implementation("androidx.activity:activity-compose:1.6.0-alpha05")
    implementation("androidx.navigation:navigation-compose:2.5.0")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation(project(":repository"))
    implementation(project(":common"))
    implementation("com.google.android.material:material:1.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-core:$koinVersion")

    // Koin for Dependency Injection
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-androidx-navigation:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")

    implementation("androidx.core:core-splashscreen:1.0.0-rc01")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}