plugins {
    id("com.android.library")
    id("module.library")
}

android {
    namespace = "nl.q42.template.domain.main"
}

dependencies {
    implementation(project(":core:actionresult"))
}
