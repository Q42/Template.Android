import com.android.build.gradle.BaseExtension

plugins {
    id("org.jetbrains.kotlin.android")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

configure<BaseExtension> {
    buildFeatures.compose = true
}

dependencies {
    "implementation"(platform(libs.findLibrary("composePlatform").get()))
    "implementation"(libs.findLibrary("composeUIGraphics").get())
    "debugImplementation"(libs.findLibrary("composeUITooling").get())
    "implementation"(libs.findLibrary("composeUIToolingPreview").get())
    "implementation"(libs.findLibrary("composeMaterial3").get())
    "implementation"(libs.findLibrary("composeLifecycle").get())
    "implementation"(libs.findLibrary("composeStateEvents").get())
    "implementation"(libs.findLibrary("koin-compose").get())
}
