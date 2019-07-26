object Kotlin {
    const val version = "1.3.31"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
}

object BuildPlugins {

    private object Versions {
        const val gradle = "3.4.1"
        const val googleService = "4.3.0"
    }

    // app  build
    const val androidApplication = "com.android.application"
    const val kapt = "kapt"
    const val androidExtension = "android.extensions"
    const val android = "android"

    // root build
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val buildGradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val googleService = "com.google.gms:google-services:${Versions.googleService}"
}

object AndroidSdk {
    const val compileVersion = 28
    const val minVersion = 21
    const val targetVersion = 28
}


object Libraries {

    private object Versions {

        const val anko = "0.10.8"
        const val appcompat = "1.1.0-alpha05"
        const val constraintLayout = "2.0.0-beta1"
        const val glide = "4.8.0"
        const val glideTransform = "4.0.0"
        const val indicator = "1.0.10"
        const val kakao = "1.17.0"
        const val koin = "2.0.0"
        const val ktx = "1.0.1"
        const val lifecycle = "2.0.0"
        const val material = "1.0.0"
        const val retrofit = "2.5.0"
        const val retrofitGson = "2.5.0"
        const val rxAndroid = "2.1.1"
        const val rxJava = "2.2.8"
        const val lottie = "3.0.7"

        const val fcm = "19.0.1"
    }

    const val kakaoGroup = "com.kakao.sdk"
    const val kakaoName = "usermgmt"
    const val kakaoVersion = Versions.kakao

    const val anko = "org.jetbrains.anko:anko:${Versions.anko}"

    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinAndroidXScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinAndroidXViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"

    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val lifeCycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideTransform = "jp.wasabeef:glide-transformations:${Versions.glideTransform}"

    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    const val indicator = "com.ryanjeffreybrooks:indefinitepagerindicator:${Versions.indicator}"
    const val fcm = "com.google.firebase:firebase-messaging:${Versions.fcm}"
}

object TestLibraries {

    private object Versions {
        const val rules = "1.1.1"
        const val espressoCore = "3.1.1"
        const val junit = "4.12"
    }

    const val rules = "androidx.test:rules:${Versions.rules}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val junit = "junit:junit:${Versions.junit}"
}

object DebugLibraries {
    private object Versions {
        const val okhttpLogger = "3.12.1"
    }

    const val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogger}"
}


object Url {

    object Maven {
        const val jfrog = "http://oss.jfrog.org/artifactory/oss-snapshot-local"
        const val bintray = "https://dl.bintray.com/android/android-tools"
        const val kakao = "http://devrepo.kakao.com:8088/nexus/content/groups/public/"
        const val jitpack = "https://jitpack.io"
        const val fabric = "https://maven.fabric.io/public"
        const val google = "https://maven.google.com"
        const val stetho = "https://github.com/WickeDev/stetho-realm/raw/master/maven-repo"
        const val zendesk = "https://zendesk.jfrog.io/zendesk/repo"
    }
}
