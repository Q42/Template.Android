import com.android.build.gradle.BaseExtension

plugins {
    id("org.jetbrains.kotlin.android")
    id("config.jvm")
    id("dep.di")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

configure<BaseExtension> {
    compileSdkVersion(rootProject.extra["compileSdkVersion"] as Int)

    defaultConfig {
        targetSdk = rootProject.extra["targetSdkVersion"] as Int
        minSdk = rootProject.extra["minSdkVersion"] as Int
    }
}

dependencies {
    "implementation"(libs.findLibrary("kermit").get())
}
