import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

val properties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    properties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "pokitmons.pokit.login"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "WEB_CLIENT_ID", properties.getProperty("web_client_id") ?: "web_client_id is empty")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // orbit
    implementation(libs.orbit.core)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)

    implementation(project(":core:ui"))
    implementation(project(":domain"))
}
