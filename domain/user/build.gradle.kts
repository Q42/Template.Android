plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.hilt)
    kapt(libs.hiltKapt)
}
