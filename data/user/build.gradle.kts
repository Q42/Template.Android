plugins {
    id("kotlin")
}

dependencies {
    implementation(project(path = ":domain:user"))
    implementation(libs.javaInject)
}