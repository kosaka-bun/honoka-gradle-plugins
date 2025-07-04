import de.honoka.gradle.buildsrc.ext.MavenPublishDsl.default

version = libs.versions.honoka.basic.plugin.get()

gradlePlugin {
    plugins {
        create("honokaBasic") {
            id = "de.honoka.gradle.plugin.basic"
            implementationClass = "de.honoka.gradle.plugin.basic.BasicPlugin"
        }
    }
}

dependencies {
    implementation("de.honoka.gradle:honoka-gradle-utils:1.0.0")
}

publishing {
    repositories {
        default()
    }
}
