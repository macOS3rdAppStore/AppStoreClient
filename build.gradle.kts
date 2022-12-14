import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.appstore"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val exposedVersion: String by project


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "15"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }

    dependencies {
        commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")
        commonMainImplementation("ru.gildor.coroutines:kotlin-coroutines-retrofit:1.1.0")
        commonMainImplementation("com.github.hazendaz.jsoup:jsoup:1.15.1")
        commonMainImplementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
        commonMainImplementation("com.squareup.retrofit2:retrofit:2.9.0")
        commonMainImplementation("com.google.code.gson:gson:2.10")

        //slf4j
        commonMainImplementation("org.slf4j:slf4j-simple:2.0.5")


        // Koin Core features
        commonMainImplementation("io.insert-koin:koin-core-jvm:3.2.2")
        commonMainImplementation("io.insert-koin:koin-core-ext:3.0.2")
        commonMainImplementation("io.insert-koin:koin-annotations-jvm:1.0.3")

        //加入sqlite数据库
        commonMainImplementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
        commonMainImplementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
        commonMainImplementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
        commonMainImplementation("org.xerial:sqlite-jdbc:3.40.0.0")

    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AppStore"
            packageVersion = "1.0.0"
        }
    }
}
