// build.gradle.kts (nivel proyecto)

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
    kotlin("kapt") version "1.9.23" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
