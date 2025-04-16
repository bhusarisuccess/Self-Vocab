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
    packaging{
        resources{
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

    }
}

dependencies {

    // Core KTX
    implementation(libs.androidx.core.ktx)
    // Kotlin BOM (Bill of Materials)
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.0.21"))
    // Lifecycle Runtime KTX
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Activity Compose
    implementation("androidx.activity:activity-compose:1.10.1")
    // Compose BOM
    implementation(libs.compose.bom.v20250300)
    // Jetpack Compose UI
    implementation(libs.ui)
    // Compose UI Graphics
    implementation(libs.ui.graphics)
    // Compose UI Tooling Preview
    implementation(libs.ui.tooling.preview)
    // Material3 for Compose
    implementation(libs.material3)
    // Unit Testing with JUnit
    testImplementation(libs.junit)
    // Android Test Extensions for JUnit
    androidTestImplementation(libs.androidx.junit)
    // Espresso for UI Testing
    androidTestImplementation(libs.androidx.espresso.core)
    // Compose BOM for Android Tests
    androidTestImplementation(libs.compose.bom.v20250300)
    // Compose UI Test JUnit4

    // Debugging dependencies
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //hilt dependenices
    implementation ("com.google.dagger:hilt-android:2.55")
    kapt ("com.google.dagger:hilt-compiler:2.54")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.54")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.navigation:navigation-compose:2.8.5")
//
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.compose.material:material-icons-extended-android:1.7.6")
    // Kotlin Coroutines Core (Required for stateIn)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Lifecycle KTX (For viewModelScope)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // ViewModel KTX (Recommended for state management)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation ("androidx.work:work-runtime-ktx:2.9.0")
    implementation ("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    implementation( "androidx.core:core-ktx:1.15.0") // or latest



    // Glance Dependencies
    implementation("androidx.glance:glance:1.1.1")
    implementation("androidx.glance:glance-material3:1.1.1")
    implementation ("androidx.glance:glance-appwidget:1.1.1")
    implementation ("androidx.work:work-runtime-ktx:2.10.0")
    implementation ("androidx.hilt:hilt-work:1.2.0")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")


}
kapt{
    correctErrorTypes = true
}