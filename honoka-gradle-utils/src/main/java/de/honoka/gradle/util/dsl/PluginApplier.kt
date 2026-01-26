package de.honoka.gradle.util.dsl

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.declarativedsl.plugins.MutablePluginDependencySpec
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

class PluginApplier(@PublishedApi internal val project: Project) : PluginDependenciesSpec {

    override fun id(id: String): PluginDependencySpec {
        project.applyPlugin(id)
        return MutablePluginDependencySpec("")
    }

    inline fun <reified T : Plugin<Project>> clazz() {
        project.apply(mapOf("plugin" to T::class.java))
    }
}

val Project.applier: PluginApplier
    get() = PluginApplier(this)

inline fun Project.applier(block: PluginApplier.() -> Unit) {
    applier.run(block)
}

fun Project.applyPlugin(id: String) {
    apply(mapOf("plugin" to id))
}
