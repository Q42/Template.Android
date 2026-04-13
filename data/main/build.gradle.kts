plugins {
    id("com.android.library")
    id("module.library")
    id("dep.network")
}

android {
    namespace = "nl.q42.template.data.main"
}

dependencies {
    implementation(project(":domain:main"))
    implementation(project(":core:network"))
    implementation(project(":core:actionresult"))
}
