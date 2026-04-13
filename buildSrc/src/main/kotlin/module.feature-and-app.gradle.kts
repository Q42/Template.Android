import com.android.build.gradle.BaseExtension

plugins {
    id("org.jetbrains.kotlin.android")
    id("config.jvm")
    id("dep.di")
    id("dep.compose")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

configure<BaseExtension> {
    compileSdkVersion(rootProject.extra["compileSdkVersion"] as Int)

    defaultConfig {
        targetSdk = rootProject.extra["targetSdkVersion"] as Int
        minSdk = rootProject.extra["minSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures.compose = true

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    "implementation"(project(":core:utils"))
    "implementation"(libs.findLibrary("kermit").get())
    "implementation"(libs.findLibrary("activityCompose").get())
    "implementation"(libs.findLibrary("koin").get())

    "testImplementation"(libs.findLibrary("junit").get())
    "testImplementation"(libs.findLibrary("kotlinx-coroutines-test").get())
    "testImplementation"(libs.findLibrary("turbine").get())
    "testImplementation"(libs.findLibrary("mockk-android").get())
    "testImplementation"(libs.findLibrary("mockk-agent").get())
}
