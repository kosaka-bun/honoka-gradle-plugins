package de.honoka.gradle.buildsrc

import de.honoka.gradle.buildsrc.ext.PublishingExt
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

fun Project.honoka(configure: Action<HonokaExt>) {
    (this as ExtensionAware).extensions.configure("honoka", configure)
}

fun HonokaExt.buildSrc(configure: Action<BuildSrcPluginExt>) {
    (this as ExtensionAware).extensions.configure("buildSrc", configure)
}

fun BuildSrcPluginExt.publishing(configure: Action<PublishingExt>) {
    (this as ExtensionAware).extensions.configure("publishing", configure)
}

fun PublishingExt.repositories(configure: Action<PublishingExt.RepositoriesExt>) {
    (this as ExtensionAware).extensions.configure("repositories", configure)
}

fun PublishingExt.publications(configure: Action<PublishingExt.PublicationsExt>) {
    (this as ExtensionAware).extensions.configure("publications", configure)
}
