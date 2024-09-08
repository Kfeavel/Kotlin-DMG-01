plugins {
    id("org.jetbrains.kotlin.multiplatform") version "2.0.20"
}

group = "gameboy"
version = "1.0-SNAPSHOT"

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {

        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
