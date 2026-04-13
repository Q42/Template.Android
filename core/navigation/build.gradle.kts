plugins {
    id("com.android.library")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    id("module.library")
    id("dep.compose")
    id("dep.navigation")
}

android {
    namespace = "nl.q42.template.core.navigation"
}

dependencies {
    implementation(libs.retrofit2.kotlinx.serialization.converter)
}
