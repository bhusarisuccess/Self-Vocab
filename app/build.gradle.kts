plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
//    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.0"

}

android {
    namespace = "com.example.self_vocab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.self_vocab"
        minSdk = 31
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
    //hilt dependenices
    implementation ("com.google.dagger:hilt-android:2.54")
    kapt ("com.google.dagger:hilt-compiler:2.54")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.54")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
//    ksp("androidx.room:room-compiler:2.5.0")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.navigation:navigation-compose:2.8.5")
//    implementation("androidx.navigation:navigation-*:2.9.0-alpha04")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.compose.material:material-icons-extended-android:1.7.6")
    // Kotlin Coroutines Core (Required for stateIn)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Lifecycle KTX (For viewModelScope)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // ViewModel KTX (Recommended for state management)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
}