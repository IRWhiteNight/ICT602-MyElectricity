/*
 * Project Name: My Electricity
 * Created by Mohamad Ikhwan Rosdi
 * Copyright © 2024. All rights reserved.
 * Last modified 05/05/2024, 9:21 am
 *
 */

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "dev.lab.myelectricity"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.lab.myelectricity"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}