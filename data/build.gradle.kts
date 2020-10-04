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