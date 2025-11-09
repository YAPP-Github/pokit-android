import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.kotlin.parcelize)
    id("kotlin-kapt")
}

android {
    namespace = "pokitmons.pokit"
    compileSdk = 36

    val properties =
        Properties().apply {
            val propFile = rootProject.file("local.properties")
            if (propFile.exists()) {
                load(propFile.inputStream())
            }
        }

    signingConfigs {
        create("release") {
            storeFile = file("${properties["release.keystore.path"]}")
            storePassword = properties["release.keystore.password"] as? String
                ?: System.getenv("RELEASE_KEYSTORE_PASSWORD")
                ?: throw GradleException("RELEASE_KEYSTORE_PASSWORD 값이 없습니다.")
            keyAlias = properties["release.key.alias"] as? String
                ?: System.getenv("RELEASE_KEY_ALIAS")
                ?: throw GradleException("RELEASE_KEY_ALIAS 값이 없습니다.")
            keyPassword = properties["release.key.password"] as? String
                ?: System.getenv("RELEASE_KEY_PASSWORD")
                ?: throw GradleException("RELEASE_KEY_PASSWORD 값이 없습니다.")
        }
    }

    defaultConfig {
        applicationId = "pokitmons.pokit"
        minSdk = 24
        targetSdk = 36
        versionCode = 4
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val url = properties["pokit.prod.url"] as? String
            ?: System.getenv("POKIT_PROD_URL")
            ?: throw GradleException("pokit.prod.url 값이 없습니다.")
        buildConfigField(type = "String", name = "BASE_URL", value = "\"$url\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
        buildConfig = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":core:ui"))
    implementation(project(":core:feature"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:addlink"))
    implementation(project(":feature:addpokit"))
    implementation(project(":feature:alarm"))
    implementation(project(":feature:uncategorized"))
    implementation(project(":feature:login"))
    implementation(project(":feature:pokitdetail"))
    implementation(project(":feature:search"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:home"))
    implementation(project(":feature:linklist"))

    // hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)

    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // orbit
    implementation(libs.orbit.compose)
    implementation(libs.orbit.core)
    implementation(libs.orbit.viewmodel)

    // kakao
    implementation(libs.kakao.share.v2)
}
