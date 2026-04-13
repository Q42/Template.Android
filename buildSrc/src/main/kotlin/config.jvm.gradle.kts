import com.android.build.gradle.BaseExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

extensions.configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.findVersion("jvmTarget").get().toString()))
    }
}

configure<BaseExtension> {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
