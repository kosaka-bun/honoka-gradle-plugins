import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publications
import de.honoka.gradle.buildsrc.publishing

version = libs.versions.p.honoka.gradle.utils.get()

honoka {
    buildSrc {
        publishing {
            publications {
                default()
            }
        }
    }
}
