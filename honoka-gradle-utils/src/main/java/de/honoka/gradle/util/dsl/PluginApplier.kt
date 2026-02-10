package de.honoka.gradle.util.dsl

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.declarativedsl.plugins.MutablePluginDependencySpec
import org.gradle.plugin.use.PluginDependencySpec

class PluginApplier internal constructor(@PublishedApi internal val project: Project) : CommonPluginsSpec {

    override fun id(id: String): PluginDependencySpec {
        project.run {
            if(plugins.hasPlugin(id)) return@run
            apply(mapOf("plugin" to id))
        }
        return MutablePluginDependencySpec("")
    }

    inline fun <reified T : Plugin<Project>> clazz() {
        val clazz = T::class.java
        project.run {
            if(plugins.hasPlugin(clazz)) return
            apply(mapOf("plugin" to clazz))
        }
    }
}

val Project.applier: PluginApplier
    get() = PluginApplier(this)

inline fun Project.applier(block: PluginApplier.() -> Unit) {
    applier.run(block)
}
