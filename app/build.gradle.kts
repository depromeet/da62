plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.androidExtension)
    kotlin(BuildPlugins.kapt)
}

android {

    compileSdkVersion(AndroidSdk.compileVersion)
    defaultConfig {
        applicationId = "com.da62"
        minSdkVersion(AndroidSdk.minVersion)
        targetSdkVersion(AndroidSdk.targetVersion)
        versionCode = Versions.code
        versionName = Versions.name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {

        getByName("release") {
            isDebuggable = false
        }
    }

    dataBinding.isEnabled = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

androidExtensions {
    isExperimental = true
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Kotlin.kotlin)

    implementation(Libraries.appcompat)
    implementation(Libraries.material)

    implementation(Libraries.constraintLayout)

    // Architecture Components
    implementation(Libraries.ktxCore)
    implementation(Libraries.lifeCycleExtension)

    // Glide
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    implementation(Libraries.glideTransform)

    // Koin
    implementation(Libraries.koinAndroid)
    implementation(Libraries.koinAndroidXScope)
    implementation(Libraries.koinAndroidXViewModel)

    // Rxjava
    implementation(Libraries.rxJava)
    implementation(Libraries.rxAndroid)

    // Retrofit with Moshi
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitRxJava)
    implementation(Libraries.retrofitGson)

    implementation(Libraries.anko)
    implementation(Libraries.lottie)

    testImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.rules)
    androidTestImplementation(TestLibraries.espressoCore)

    implementation(DebugLibraries.okhttpLogger)

    implementation(Libraries.lottie)

    implementation(Libraries.indicator)
    implementation(Libraries.fcm)

    // Kakao
    implementation(
        group = Libraries.kakaoGroup,
        name = Libraries.kakaoName,
        version = Libraries.kakaoVersion
    )
}
apply(mapOf("plugin" to "com.google.gms.google-services"))