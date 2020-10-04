allprojects {
    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap/")
        jcenter()
        google()
        mavenCentral()
    }
}

buildscript {

    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap/")
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha13")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath(kotlin("serialization", version = Versions.kotlinVersion))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
