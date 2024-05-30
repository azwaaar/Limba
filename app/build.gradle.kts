plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.gms.application)
}

android {
    namespace = "id.co.bitdata.b3"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.co.bitdata.b3"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding.enable = true
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.stepstone)
    implementation(libs.location)
    implementation(libs.maps)
    implementation(libs.bottomFlux)
    implementation(libs.chip)
    implementation(libs.circleImage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}