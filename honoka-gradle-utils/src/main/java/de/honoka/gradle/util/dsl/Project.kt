package de.honoka.gradle.util.dsl

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler
import org.gradle.api.internal.catalog.VersionModel
import org.gradle.api.internal.project.ProjectStateInternal
import org.gradle.api.publish.PublishingExtension

fun Project.applyPlugin(id: String) {
    apply(mapOf("plugin" to id))
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
                set.add(dep)
            }
        }
        return set
    }

val Project.currentProject: Project
    get() = rootProject.allprojects.first { (it.state as ProjectStateInternal).isConfiguring }

@Suppress("UNCHECKED_CAST")
fun Project.libVersions(): Map<String, VersionModel> {
    val libs = rootProject.extensions.getByName("libs")
    val versions = libs.javaClass.getDeclaredMethod("getVersions").invoke(libs)
    val catalog = versions.javaClass.superclass.getDeclaredField("config").run {
        isAccessible = true
        get(versions)
    }
    catalog.javaClass.getDeclaredField("versions").run {
        isAccessible = true
        return get(catalog) as Map<String, VersionModel>
    }
}

fun Project.projects(vararg names: String): List<Project> = run {
    listOf(*names).map { project(":$it") }
}

fun Project.publishing(configure: Action<PublishingExtension>) {
    extensions.configure("publishing", configure)
}
