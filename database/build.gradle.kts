val koinVersion: String by project
val roomVersion: String by project
val compileSDKVersion: String by project
val minSDKVersion: String by project
val targetSDKVersion: String by project

plugins {
    id("com.google.devtools.ksp")
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = compileSDKVersion.toInt()

    defaultConfig {
        minSdk = minSDKVersion.toInt()
        targetSdk = targetSDKVersion.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
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

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    implementation(project(":common"))

    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    androidTestImplementation("io.insert-koin:koin-test:$koinVersion")
    androidTestImplementation("io.insert-koin:koin-android:$koinVersion")
}