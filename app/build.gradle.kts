plugins {
    id("com.android.application")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlyticsPlugin)
    alias(libs.plugins.compose.compiler)
    id("app.cash.licensee")
    id("module.feature-and-app")
    id("dep.navigation")
}

if (file("$rootDir/secrets.gradle.kts").exists()) {
    apply(from = "$rootDir/secrets.gradle.kts")
} else {
    println("[WARNING] Using dummy secrets file. You will only be able to create debug builds. Please create a secrets.gradle.kts file in the project root with your signing secrets if you want to create release builds.")
    apply(from = "$rootDir/dummy_secrets.gradle.kts")
}

val appScheme = "template"

@Suppress("UNCHECKED_CAST")
val signingSecrets = extra["signingSecrets"] as Map<String, Map<String, String>>

android {

    namespace = "nl.q42.template"

    defaultConfig {
        versionCode = 1 // version code is set by CI
        versionName = "1.0"

        buildConfigField("String", "config_app_scheme", "\"$appScheme\"")
        manifestPlaceholders["appScheme"] = appScheme
    }

    buildFeatures { // only app may have buildConfigFields
        buildConfig = true
    }

    packaging {
        resources {
            // Exclude duplicate META-INF files from dependencies to avoid build conflicts
            // okhttp3:logging-interceptor and jspecify both contain META-INF/versions/9/OSGI-INF/MANIFEST.MF
            excludes += "**/META-INF/versions/*/OSGI-INF/MANIFEST.MF"
        }
    }

    signingConfigs {
        create("upload") {
            storeFile = file("../upload-keystore.jks")
            storePassword = signingSecrets["upload"]?.get("storePassword")
            keyAlias = signingSecrets["upload"]?.get("keyAlias")
            keyPassword = signingSecrets["upload"]?.get("keyPassword")
        }
    }

    buildTypes {
        debug {
            buildConfigField("boolean", "config_log_http_calls", "true")
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("upload")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "config_log_http_calls", "false")
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            val name = "dev"
            versionNameSuffix = "-$name"
            applicationIdSuffix = ".$name"
            buildConfigField("String", "config_api_main_url", "\"https://httpbin.org/\"")
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "config_api_main_url", "\"https://httpbin.org/\"")
        }
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:onboarding"))
    implementation(project(":domain:main"))  // needed for di
    implementation(project(":data:main"))  // needed for di
    implementation(project(":core:network")) // needed for di
    implementation(libs.composeNavigation)

    api(platform(libs.firebaseBoM))
    implementation(libs.firebaseCrashlytics)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.koin.test)
}

tasks.register("checkSigningConfig") {
    doFirst {
        val cfg = android.signingConfigs.getByName("upload")
        if (cfg.storeFile?.exists() != true) {
            throw GradleException("Keystore file for Signing config 'upload' is not found.")
        } else if (
            cfg.storePassword.isNullOrEmpty() ||
            cfg.keyAlias.isNullOrEmpty() ||
            cfg.keyPassword.isNullOrEmpty()
        ) {
            throw GradleException("Signing config 'upload' is missing values for release build.")
        }
    }
}

// Attach checkSigningConfig to all release variant preBuild tasks
tasks.matching { it.name.matches(Regex("assemble.*Release")) }.configureEach {
    dependsOn("checkSigningConfig")
}

licensee { // A gradle task "./gradlew licensee" checks the licenses of your dependencies and fails when a disallowed license is found.
    allow("Apache-2.0")
    allow("BSD-3-Clause")
    allow("MIT")
    allowUrl("https://developer.android.com/studio/terms.html")
    allowDependency("com.github.leonard-palm", "compose-state-events", "2.2.0") {
        because("Apache-2.0, but license in this version of the lib is not recognized by Licensee")
    }
}
