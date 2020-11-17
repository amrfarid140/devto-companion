object Versions {
    const val composeVersion = "1.0.0-alpha07"
    const val kotlinVersion = "1.4.10"
    const val coroutinesVersion = "1.3.9-native-mt-2"
    const val ktorVersion = "1.4.1"
    const val serializationVersion = "1.0.0-RC2"
}

object Dependencies {
    val jetpackCompose =
        mapOf(
            "ui" to "androidx.compose.ui:ui:${Versions.composeVersion}",
            "tooling" to "androidx.ui:ui-tooling:${Versions.composeVersion}",
            "foundation" to "androidx.compose.foundation:foundation:${Versions.composeVersion}",
            "materialDesign" to "androidx.compose.material:material:${Versions.composeVersion}",
            "materialDesignIcons" to "androidx.compose.material:material-icons-extended:${Versions.composeVersion}",
            "navigation" to "androidx.navigation:navigation-compose:1.0.0-alpha02"
        )

    const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val coil = "io.coil-kt:coil:0.11.0"
    const val androidCore = "androidx.core:core-ktx:1.3.0"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val materialComponents = "com.google.android.material:material:1.1.0"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
    const val ktorCIO = "io.ktor:ktor-client-cio:${Versions.ktorVersion}"
    const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktorVersion}"
    const val ktoriOS = "io.ktor:ktor-client-ios:${Versions.ktorVersion}"
    const val ktorJson = "io.ktor:ktor-client-json:${Versions.ktorVersion}"
    const val ktorSerialization = "io.ktor:ktor-client-serialization:${Versions.ktorVersion}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationVersion}"
}