import de.honoka.gradle.buildsrc.setupPublication

version = libs.versions.honoka.gradle.utils.get()

publishing {
    setupPublication(project)
}
