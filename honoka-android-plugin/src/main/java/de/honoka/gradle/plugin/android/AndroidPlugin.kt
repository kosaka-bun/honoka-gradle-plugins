@file:Suppress("unused")

package de.honoka.gradle.plugin.android

import de.honoka.gradle.plugin.android.ext.AndroidExt
import de.honoka.gradle.util.dsl.applyPlugin
import de.honoka.gradle.util.dsl.asExt
import de.honoka.gradle.util.dsl.defineDsl
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val basicPluginName = "de.honoka.gradle.plugin.basic"
        project.run {
            if(!plugins.hasPlugin(basicPluginName)) {
                applyPlugin(basicPluginName)
            }
            extensions.getByName("honoka").asExt().run {
                defineDsl("android", AndroidExt::class)
            }
        }
    }
}
