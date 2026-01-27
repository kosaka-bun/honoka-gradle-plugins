import de.honoka.gradle.plugin.basic.dsl.publishing

honoka.basic.publishing.gradlePluginVersion = libs.versions.p.honoka.android.plugin.get()

dependencies {
    implementation(libs.honoka.basic.plugin)
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
