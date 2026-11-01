plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.seunome.leitorpdf" // Pode mudar "seunome" para o seu nome
    compileSdk = 34

    defaultConfig {
        applicationId = "com.seunome.leitorpdf"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Biblioteca para ler PDF
    implementation("androidx.pdf:pdf-viewer-fragment:1.0.0-alpha02")
}