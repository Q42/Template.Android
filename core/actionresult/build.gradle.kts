plugins {
    id("com.android.library")
    id("module.library")
    id("dep.network")
}

android {
    namespace = "nl.q42.template.core.actionresult"
}

dependencies {
    implementation(libs.networkResponseAdapter)
}
