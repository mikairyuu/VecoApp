plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("org.jlleitschuh.gradle.ktlint")
}

val composeVersion = "1.2.0"
val accompanistVersion = "0.25.1"

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.veco.vecoapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
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
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.runtime:runtime-rxjava2:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.5.1")
    implementation("androidx.compose.material:material:$composeVersion")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")

    implementation("me.onebone:toolbar-compose:2.3.4")
    implementation("io.coil-kt:coil-compose:2.2.0")
    implementation("androidx.compose.material3:material3:1.0.0-beta01")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.0-beta01")
}
