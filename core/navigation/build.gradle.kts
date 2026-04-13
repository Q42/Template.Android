plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
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
