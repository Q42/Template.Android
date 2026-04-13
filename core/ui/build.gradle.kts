plugins {
    id("com.android.library")
    alias(libs.plugins.compose.compiler)
    id("module.library")
    id("dep.compose")
}

android {
    namespace = "nl.q42.template.core.ui"
}
