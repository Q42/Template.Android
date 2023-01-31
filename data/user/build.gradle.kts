plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}


android {

    val ext = rootProject.extra

    namespace = "nl.q42.data.user"
    compileSdk = ext["compileSdkVersion"] as Int

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

    implementation(libs.javaInject)
}
