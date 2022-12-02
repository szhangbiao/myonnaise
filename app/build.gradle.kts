plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.COMPILE_SDK
    defaultConfig {
        applicationId = "it.ncorti.emgvisualizer"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = 3
        versionName = "2.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    implementation(project(":myonnaise"))
    implementation(project(":sensorgraphview"))

    implementation(Libs.kotlinJdk)

    implementation(Libs.APPCOMPAT)
    implementation(Libs.androidXConstraintLayout)
    implementation(Libs.androidXRecyclerView)
    implementation(Libs.materialComponents)
    implementation(Libs.CORE_KTX)

    testImplementation(Libs.junit)
    testImplementation(Libs.mockitoCore)
    testImplementation(Libs.mockitoKotlin)

    implementation(Libs.rxjava2)
    implementation(Libs.rxAndroid2)

    implementation(Libs.HILT_ANDROID)
    kapt(Libs.HILT_COMPILER)

    implementation(Libs.appIntro)
    implementation(Libs.retrofit)
    implementation(Libs.okHttp)
    implementation(Libs.gson)
    implementation(Libs.interceptor)
    implementation(Libs.gsonConverter)
    implementation(Libs.rxjavaAdapter)
}
