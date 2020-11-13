buildscript {

    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap/")
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha16")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath(kotlin("serialization", version = Versions.kotlinVersion))
    }
}

subprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap/")
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
