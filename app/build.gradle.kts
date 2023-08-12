plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.bagus2x.toko"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.bagus2x.toko"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "HTTP_BASE_URL", """ "https://keraton.indward.com" """)
        }
        release {
            buildConfigField("String", "HTTP_BASE_URL", """ "https://keraton.indward.com" """)
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        isCoreLibraryDesugaringEnabled = true
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
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    implementation("androidx.core:core-ktx:1.10.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    implementation("androidx.activity:activity-compose:1.7.2")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-text-google-fonts")
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation Compose
    //noinspection GradleDependency
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Constraint Layout Compose
    //noinspection GradleDependency
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha10")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Hilt Android
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    // Hilt Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // OSM
    implementation("org.osmdroid:osmdroid-android:6.1.13")

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Ktor
    val ktorVersion = "2.3.3"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Preference
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    // Camerax
    val cameraxVersion = "1.2.3"
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")
    implementation("androidx.camera:camera-extensions:${cameraxVersion}")

    // Room
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Paging Compose
    implementation("androidx.paging:paging-compose:3.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
    correctErrorTypes = true
}
