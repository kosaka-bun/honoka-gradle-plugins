package de.honoka.gradle.plugin.basic

import de.honoka.gradle.plugin.basic.ext.MavenPublishDsl
import de.honoka.gradle.plugin.basic.model.GlobalData
import de.honoka.gradle.plugin.basic.model.globalData
import org.gradle.api.Plugin
import org.gradle.api.Project

class BasicPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        globalData = GlobalData().apply {
            rootProject = project.rootProject
        }
        project.extensions.create("honokaBasic", BasicPluginDsl::class.java, project)
    }
}

abstract class BasicPluginDsl(private val project: Project) {

    fun publishing(block: MavenPublishDsl.() -> Unit) {
        MavenPublishDsl(project).apply(block)
    }
}
