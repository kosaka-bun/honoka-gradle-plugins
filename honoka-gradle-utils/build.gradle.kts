import de.honoka.gradle.buildsrc.ext.MavenPublishDsl.setupPublication

version = libs.versions.honoka.gradle.utils.get()

publishing {
    setupPublication()
}
