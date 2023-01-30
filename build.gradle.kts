// Top-level build file where you can add configuration options common to all sub-projects/modules.

// TODO: remove once this is fixed: https://youtrack.jetbrains.com/issue/KTIJ-19369
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    // set the plugins and their versions on the classpath:
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.hilt) apply false
}

buildscript {
    extra.apply {
        set("minSdkVersion", 29)
        set("targetSdkVersion", 33)
        set("compileSdkVersion", 33)
    }
}