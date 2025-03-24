plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")// Enables annotation processing in Kotlin
    alias(libs.plugins.dagger) //enables Hilt's build-time code generation.


}

android {
    namespace = "com.example.image_search_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.image_search_app"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // gson converter

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //pagination
    implementation(libs.androidx.paging.compose)

    //dagger hilt
    implementation(libs.hilt.android) // dagger hilt core dependency
    kapt(libs.hilt.android.compiler) // annotation processor for auto generation of code
    implementation(libs.androidx.hilt.navigation.compose) //provides integration between Hilt and Jetpack Compose navigation & also provide hiltViewModel(). like functions
}