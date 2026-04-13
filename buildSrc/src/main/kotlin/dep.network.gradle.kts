val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    "implementation"(project(":core:utils"))
    "implementation"(libs.findLibrary("retrofit").get())
    "implementation"(libs.findLibrary("kotlinx-serialization-json").get())
    "implementation"(libs.findLibrary("retrofit2-kotlinx-serialization-converter").get())
    "implementation"(libs.findLibrary("networkResponseAdapter").get())
    "implementation"(libs.findLibrary("okhttp").get())
    "implementation"(libs.findLibrary("okhttpLogging").get())
}
