package de.honoka.gradle.buildsrc

import de.honoka.gradle.buildsrc.ext.PublishingExt
import de.honoka.gradle.buildsrc.util.dsl.DslContainer
import de.honoka.gradle.buildsrc.util.dsl.defineDsl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

class BuildSrcPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.defineDsl("honoka", HonokaExt::class)
    }
}

open class HonokaExt : DslContainer() {

    override val defineDslsAction: ExtensionAware.() -> Unit = {
        defineDsl("buildSrc", BuildSrcPluginExt::class)
    }
}

open class BuildSrcPluginExt : DslContainer() {

    override val defineDslsAction: ExtensionAware.() -> Unit = {
        defineDsl("publishing", PublishingExt::class)
    }
}
