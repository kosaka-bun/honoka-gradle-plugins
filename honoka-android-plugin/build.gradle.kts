version = libs.versions.p.honoka.android.plugin.get()

dependencies {
    implementation(libs.honoka.basic.plugin)
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

honoka.buildSrc {
    publishing {
        repositories {
            default()
        }
    }
}
