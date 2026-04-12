import de.honoka.gradle.plugin.basic.dsl.publishing

honoka.basic.publishing.gradlePluginVersion = libs.versions.p.honoka.spring.plugin.get()

dependencies {
    implementation(libs.honoka.basic.plugin)
    compileOnly(libs.spring.boot.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("honokaSpring") {
            id = "de.honoka.gradle.plugin.spring"
            implementationClass = "de.honoka.gradle.plugin.spring.SpringPlugin"
        }
    }
}
