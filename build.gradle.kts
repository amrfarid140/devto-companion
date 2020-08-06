allprojects {
    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap/")
        jcenter()
        google()
        mavenCentral()
    }
}

buildscript {
    extra.apply {
        set("compose_version", "0.1.0-dev16")
        set("kotlin_version","1.4.0-rc")
        set("ktor_version","1.3.2-1.4-M3")
        set("coroutines_version","1.3.7-1.4-M3")
        set("serialization_version","0.20.0-1.4-M3")
    }

    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap/")
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha07")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra.get("kotlin_version")}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${rootProject.extra.get("kotlin_version")}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
