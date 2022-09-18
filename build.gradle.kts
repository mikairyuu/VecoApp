buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("dev.icerock.moko:resources-generator:0.20.1")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.3.0")
        classpath("io.realm.kotlin:gradle-plugin:1.1.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
