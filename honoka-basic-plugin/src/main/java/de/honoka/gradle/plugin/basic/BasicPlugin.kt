package de.honoka.gradle.plugin.basic

import de.honoka.gradle.plugin.basic.ext.BasicExt
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
        defineDsl("basic", BasicExt::class)
    }
}
