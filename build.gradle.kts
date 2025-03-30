buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

// TODO: вроде, сюда надо было добавить навигатор или что-то типа этого, но его здесь нет и все работает.
// TODO: почему? - хз. разобраться
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}