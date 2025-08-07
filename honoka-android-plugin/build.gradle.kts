import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.repositories

version = libs.versions.p.honoka.android.plugin.get()

java {
    toolchain.languageVersion = JavaLanguageVersion.of(11)
}

dependencies {
    implementation(libs.hap.honoka.basic.plugin)
    compileOnly(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("honokaAndroid", Action {
            id = "de.honoka.gradle.plugin.android"
            implementationClass = "de.honoka.gradle.plugin.android.AndroidPlugin"
        })
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
