import de.honoka.gradle.buildsrc.ext.MavenPublishDsl.default

version = libs.versions.honoka.gradle.utils.get()

publishing {
    publications {
        default()
    }
}
