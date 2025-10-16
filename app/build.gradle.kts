plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.monclassementfoot"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.monclassementfoot"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")

    // Jetpack Compose UI et Material3
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Tests unitaires
    testImplementation("junit:junit:4.13.2")

// import pour choix couleurs equipes
    implementation("com.google.accompanist:accompanist-flowlayout:0.30.1")

    implementation("androidx.compose.foundation:foundation:1.5.0")
}
