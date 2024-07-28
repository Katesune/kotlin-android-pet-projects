import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.katesune.filmograf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.katesune.filmograf"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val localPropFile = project.rootProject.file("local.properties")
    val properties = Properties()

    val keystorePassAlias = "FILMOGRAF_KEYSTORE_PASSWORD"
    val keystoreKeyAlias = "FILMOGRAF_RELEASE_SIGN_KEY_ALIAS"
    val keystoreKeyPassAlias = "FILMOGRAF_RELEASE_SIGN_KEY_PASSWORD"

    signingConfigs {
        create("release") {
            if (localPropFile.exists()) {

                properties.load(localPropFile.inputStream())
                storeFile = file(properties.getProperty("keystore_file"))
                storePassword = properties.getProperty(keystorePassAlias)
                keyAlias = properties.getProperty(keystoreKeyAlias)
                keyPassword = properties.getProperty(keystoreKeyPassAlias)

            } else {

                storeFile = file("keystore/upload-keystore.jks")
                storePassword = System.getenv(keystorePassAlias)
                keyAlias = System.getenv(keystoreKeyAlias)
                keyPassword = System.getenv(keystoreKeyPassAlias)

            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
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

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    val koin_version = "3.2.0-beta-1"
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation ("io.insert-koin:koin-android:$koin_version")

    testImplementation ("io.insert-koin:koin-test-junit4:$koin_version")
    implementation("androidx.compose.material3:material3:1.2.0-rc01")

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
}