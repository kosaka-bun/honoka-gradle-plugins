package de.honoka.gradle.buildsrc

import de.honoka.gradle.buildsrc.ext.PublishingExt
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.get

val Project.honoka: HonokaExt
    get() = (this as ExtensionAware).extensions["honoka"] as HonokaExt

fun Project.honoka(configure: Action<HonokaExt>) {
    (this as ExtensionAware).extensions.configure("honoka", configure)
}

fun HonokaExt.buildSrc(configure: Action<BuildSrcPluginExt>) {
    (this as ExtensionAware).extensions.configure("buildSrc", configure)
}

fun BuildSrcPluginExt.publishing(configure: Action<PublishingExt>) {
    (this as ExtensionAware).extensions.configure("publishing", configure)
}
