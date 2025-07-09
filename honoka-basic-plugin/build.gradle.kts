import de.honoka.gradle.buildsrc.ext.MavenPublishDsl.default

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

publishing {
    repositories {
        default()
    }
}
