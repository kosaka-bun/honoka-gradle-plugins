import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.repositories
import de.honoka.gradle.buildsrc.util.dsl.implementationApi

version = libs.versions.p.honoka.basic.plugin.get()

dependencies {
    implementationApi(libs.hbp.honoka.gradle.utils)
}

gradlePlugin {
    plugins {
        create("honokaBasic") {
            id = "de.honoka.gradle.plugin.basic"
            implementationClass = "de.honoka.gradle.plugin.basic.BasicPlugin"
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
