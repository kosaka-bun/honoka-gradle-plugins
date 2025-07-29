@file:Suppress("unused")

package de.honoka.gradle.plugin.basic

import de.honoka.gradle.plugin.basic.ext.DependenciesExt
import de.honoka.gradle.plugin.basic.ext.PublishingExt
import de.honoka.gradle.util.dsl.DslContainer
import de.honoka.gradle.util.dsl.defineDsl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

class BasicPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.defineDsl("honoka", HonokaExt::class)
    }
}

open class HonokaExt : DslContainer() {

    override val defineDslsAction: ExtensionAware.() -> Unit = {
        defineDsl("basic", BasicPluginExt::class)
    }
}

open class BasicPluginExt : DslContainer() {

    override val defineDslsAction: ExtensionAware.() -> Unit = {
        defineDsl("dependencies", DependenciesExt::class)
        defineDsl("publishing", PublishingExt::class)
    }
}
