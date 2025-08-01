@file:Suppress("unused")

package de.honoka.gradle.plugin.android

import de.honoka.gradle.util.dsl.applyPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val basicPluginName = "de.honoka.gradle.plugin.basic"
        project.run {
            if(plugins.hasPlugin(basicPluginName)) return
            applyPlugin(basicPluginName)
        }
    }
}
