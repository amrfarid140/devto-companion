import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

kotlin {

    targets {
        ios {
            binaries {
                framework {
                    baseName = "data"
                }
            }
        }

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
                // Coroutines
                implementation(Dependencies.coroutines){
                    isForce = true
                }

                // Ktor
                implementation(Dependencies.ktorCore)
                implementation(Dependencies.ktorJson)
                implementation(Dependencies.ktorSerialization)

                // Serialize
                implementation(Dependencies.serialization)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependencies.ktorAndroid)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Dependencies.ktoriOS)
            }
        }
    }

    (kotlin.targets.first { it.name.contains("ios") } as KotlinNativeTarget)
        .compilations["main"].kotlinOptions.freeCompilerArgs += listOf("-Xobjc-generics", "-Xg0")
}
