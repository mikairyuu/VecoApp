plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.veco.vecoapp.android"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Compose
    implementation("androidx.compose.ui:ui:1.2.0")
    implementation("androidx.compose.ui:ui-tooling:1.2.0")
    implementation("androidx.compose.foundation:foundation:1.2.0")
    implementation("androidx.compose.material:material-icons-core:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.2.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.0")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.2.0")
}