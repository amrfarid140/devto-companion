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
                    this.embedBitcode
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
                implementation(project(":KMPNetworking"))
                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8-native-mt-1.4.0-rc"){
                    isForce = true
                }

                // Ktor
                implementation("io.ktor:ktor-client-core:1.3.2-1.4.0-rc")
                implementation("io.ktor:ktor-client-json:1.3.2-1.4.0-rc")
                implementation("io.ktor:ktor-client-serialization:1.3.2-1.4.0-rc")

                // Serialize
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0-1.4.0-rc-95")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:1.3.2-1.4.0-rc")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.3.2-1.4.0-rc")
            }
        }
    }

    (kotlin.targets.first { it.name.contains("ios") } as KotlinNativeTarget)
        .compilations["main"].kotlinOptions.freeCompilerArgs += listOf("-Xobjc-generics", "-Xg0")
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS 
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName: String? = System.getenv("SDK_NAME")
    val isiOSDevice = false
    val framework = kotlin.targets
            .getByName<KotlinNativeTarget>(
                if(isiOSDevice) {
                    "iosArm64"
                } else {
                    "iosX64"
                }
            )
            .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)