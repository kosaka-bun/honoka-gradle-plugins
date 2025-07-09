import de.honoka.gradle.buildsrc.ext.MavenPublishDsl.default

version = libs.versions.p.honoka.gradle.utils.get()

publishing {
    publications {
        default()
    }
}
