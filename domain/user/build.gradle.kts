plugins {
    id("com.android.library")
    kotlin("android")
}
// TODO: Would be nice to make this kotlin once again
android {
    namespace = "nl.q42.domain.user"
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":data:user"))
    implementation(libs.javaInject)
}
