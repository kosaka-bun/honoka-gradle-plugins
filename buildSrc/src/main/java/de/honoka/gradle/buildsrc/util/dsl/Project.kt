package de.honoka.gradle.buildsrc.util.dsl

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler
import org.gradle.api.internal.project.ProjectStateInternal
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.publish.PublishingExtension
import java.io.File
import java.util.jar.JarFile

val PluginContainer.versions: Map<String, String?>
    get() {
        val map = HashMap<String, String?>()
        forEach { p ->
            val jarUrl = p.javaClass.protectionDomain.codeSource.location
            val jarFile = File(jarUrl.toURI()).run {
                if(!exists()) return@forEach
                JarFile(this)
            }
            val idList = run {
                val prefix = "META-INF/gradle-plugins/"
                val suffix = ".properties"
                jarFile.entries().asSequence().filter {
                    it.name.startsWith(prefix) && it.name.endsWith(suffix)
                }.map {
                    it.name.removePrefix(prefix).removeSuffix(suffix)
                }.filter {
                    runCatching {
                        hasPlugin(it)
                    }.getOrDefault(false)
                }
            }
            val fileName = jarUrl.path.lowercase().run {
                split('/').last().removeSuffix(".jar")
            }
            val version = fileName.split('-').run {
                val index = indexOfFirst { it.contains('.') }
                if(index > 0) subList(index, size).joinToString("-") else null
            }
            idList.forEach {
                map[it] = version
            }
        }
        return map
    }

val Project.rawDependencies: Set<Dependency>
    get() {
        val configurationContainerField = DefaultDependencyHandler::class.java.run {
            getDeclaredField("configurationContainer")
        }
        val configurationContainer = configurationContainerField.run {
            isAccessible = true
            get(dependencies) as ConfigurationContainer
        }
        val set = HashSet<Dependency>()
        configurationContainer.forEach {
            it.dependencies.forEach { dep ->
                set.add(dep as Dependency)
            }
        }
        return set
    }

val Project.currentProject: Project
    get() = rootProject.allprojects.first { (it.state as ProjectStateInternal).isConfiguring }

fun Project.projects(vararg names: String): List<Project> = run {
    listOf(*names).map { project(":$it") }
}

fun Project.publishing(configure: Action<PublishingExtension>) {
    extensions.configure("publishing", configure)
}
