import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.repositories

version = libs.versions.p.honoka.basic.plugin.get()

gradlePlugin {
    plugins {
        create("honokaBasic") {
            id = "de.honoka.gradle.plugin.basic"
            implementationClass = "de.honoka.gradle.plugin.basic.BasicPlugin"
        }
    }
}

dependencies {
    implementation(libs.hbp.honoka.gradle.utils)
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
