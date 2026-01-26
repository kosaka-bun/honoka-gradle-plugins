package de.honoka.gradle.plugin.basic

import de.honoka.gradle.plugin.basic.ext.ConfigsExt
import de.honoka.gradle.plugin.basic.ext.DependenciesExt
import de.honoka.gradle.plugin.basic.ext.PublishingExt
import de.honoka.gradle.util.dsl.BaseExtension
import de.honoka.gradle.util.dsl.defineDsl
import org.gradle.api.Plugin
import org.gradle.api.Project

class BasicPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.defineDsl("honoka", HonokaExt::class)
    }
}

open class HonokaExt : BaseExtension() {

    override fun defineDsls() {
        defineDsl("basic", BasicPluginExt::class)
    }
}

open class BasicPluginExt : BaseExtension() {

    override fun defineDsls() {
        defineDsl("dependencies", DependenciesExt::class)
        defineDsl("publishing", PublishingExt::class)
        defineDsl("configs", ConfigsExt::class)
    }
}
