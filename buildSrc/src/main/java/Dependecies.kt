object Versions {
    const val COMPILE_SDK = 30
    const val TARGET_SDK = 30
    const val MIN_SDK = 21

    const val androidGradle = "7.2.2"
    const val appcompat = "1.3.1"
    const val recyclerview = "1.2.1"
    const val appintro = "v5.1.0"
    const val hilt = "2.42"
    const val constraintlayout = "2.1.4"
    const val junit = "4.12"
    const val ktLint = "8.0.0"
    const val kotlin = "1.7.10"
    const val materialcomponents = "1.0.0"
    const val mockito = "2.23.4"
    const val mockitoKotlin = "2.1.0"
    const val rxjava2 = "2.2.12"
    const val rxandroid2 = "2.1.1"
    const val core = "1.6.0-rc01"
    const val retrofit = "2.9.0"
    const val okhttp = "4.10.0"
    const val gson = "2.8.9"
    const val exoPlayer = "2.13.3"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val ktLintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktLint}"

    const val kotlinJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidXRecyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val materialComponents = "com.google.android.material:material:${Versions.materialcomponents}"
    const val androidXConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxjava2}"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid2}"
    const val appIntro = "com.github.paolorotolo:appintro:${Versions.appintro}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.core}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.1.0"
    const val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:2.6.2"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val playerCore = "com.google.android.exoplayer:exoplayer-core:${Versions.exoPlayer}"
    const val playerUI = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoPlayer}"
}