plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.freetime.idlecoding"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.freetime.idlecoding"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0.0"
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    lint {
        // Existing issues are captured in lint-baseline.xml so CI fails only on
        // NEW problems introduced by future changes, without forcing a cleanup of
        // the pre-existing backlog. Regenerate by deleting the baseline and
        // running `./gradlew lintDebug`.
        baseline = file("lint-baseline.xml")
        warningsAsErrors = false
        warning += listOf("MissingTranslation", "MissingQuantity")
    }

    testOptions {
        // Let Robolectric (Room migration tests) access Android resources.
        unitTests.isIncludeAndroidResources = true
    }
}

// Export Room schema files so migrations can be verified and reviewed in version control.
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    ksp(libs.androidx.hilt.compiler)

    // AppCompat (per-app locale on API 21+)
    implementation(libs.androidx.appcompat)

    // kotlinx.serialization
    implementation(libs.kotlinx.serialization.json)

    // Unit testing (JVM, no device required)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)

    // Room migration testing under Robolectric (runs on the JVM, no emulator)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)
}
