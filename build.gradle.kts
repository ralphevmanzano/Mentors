// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradleTools)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.safeArgsPlugin)
        classpath(Libs.hiltPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10-RC")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}