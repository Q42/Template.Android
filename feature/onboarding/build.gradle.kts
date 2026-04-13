plugins {
    id("com.android.library")
    alias(libs.plugins.compose.compiler)
    id("module.feature-and-app")
    id("dep.navigation")
}

android {
    val moduleName = "onboarding"

    namespace = "nl.q42.template.feature.$moduleName"
}

dependencies {
    implementation(project(":domain:main"))
    implementation(project(":core:ui"))
    implementation(project(":core:actionresult"))
    implementation(project(":core:navigation"))
}
