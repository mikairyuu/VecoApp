plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

version = "1.0"
val mvvmVersion = "0.13.1"
val ktorVersion = "2.1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosVeco/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.kodein.di:kodein-di:7.14.0")
                api("dev.icerock.moko:mvvm-core:$mvvmVersion")
                api("dev.icerock.moko:mvvm-flow:$mvvmVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("dev.icerock.moko:resources-test:0.20.1")
            }
        }
        val androidMain by getting {
            dependencies {
                api("dev.icerock.moko:resources-compose:0.20.1")
                api("dev.icerock.moko:mvvm-flow-compose:$mvvmVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-native-mt") {
                    version {
                        strictly("1.6.0-native-mt")
                    }
                }
            }
        }
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

dependencies {
    commonMainApi("dev.icerock.moko:resources:0.20.1")
}

multiplatformResources {
    multiplatformResourcesPackage = "com.veco.vecoapp"
    iosBaseLocalizationRegion = "ru"
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
}
