import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.repositories

version = libs.versions.p.honoka.android.plugin.get()

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = sourceCompatibility
}

dependencies {
    implementation(libs.hap.honoka.basic.plugin)
    compileOnly(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("honokaAndroid") {
            id = "de.honoka.gradle.plugin.android"
            implementationClass = "de.honoka.gradle.plugin.android.AndroidPlugin"
        }
    }
}

honoka {
    buildSrc {
        publishing {
            repositories {
                default()
            }
        }
    }
}
