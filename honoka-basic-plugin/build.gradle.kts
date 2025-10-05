import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.repositories

version = libs.versions.p.honoka.basic.plugin.get()

dependencies {
    api(libs.honoka.gradle.utils)
}

gradlePlugin {
    plugins {
        create("honokaBasic", Action {
            id = "de.honoka.gradle.plugin.basic"
            implementationClass = "de.honoka.gradle.plugin.basic.BasicPlugin"
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
