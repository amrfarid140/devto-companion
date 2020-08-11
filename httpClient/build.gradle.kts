import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

kotlin {

    targets {
        ios()
        jvm("android")
    }

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        val commonMain by getting {
            dependencies {
                // Serialize
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0-1.4.0-rc-95")
            }
        }

        val androidMain by getting {
            dependencies {}
        }

        val iosMain by getting {
            dependencies {}
        }
    }
}
