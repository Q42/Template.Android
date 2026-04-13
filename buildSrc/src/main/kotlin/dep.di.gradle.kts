val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    "implementation"(libs.findLibrary("koin").get())
}
