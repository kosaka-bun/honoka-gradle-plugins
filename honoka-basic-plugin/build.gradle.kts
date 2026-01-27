import de.honoka.gradle.plugin.basic.dsl.publishing

honoka.basic.publishing.gradlePluginVersion = libs.versions.p.honoka.basic.plugin.get()

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    api(libs.honoka.gradle.utils)
}

gradlePlugin {
    plugins {
        create("honokaBasic") {
            id = "de.honoka.gradle.plugin.basic"
            implementationClass = "de.honoka.gradle.plugin.basic.BasicPlugin"
        }
    }
}
