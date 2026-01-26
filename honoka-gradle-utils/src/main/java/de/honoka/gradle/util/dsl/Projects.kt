package de.honoka.gradle.util.dsl

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler
import org.gradle.api.internal.catalog.VersionModel
import org.gradle.api.internal.project.ProjectStateInternal

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

fun Project.libVersions(): Map<String, String> {
    val result = HashMap<String, String>()
    listOf("commonLibs", "libs").forEach {
        result.putAll(parseLibVersions(this, it))
    }
    return result
}

@Suppress("UNCHECKED_CAST")
private fun parseLibVersions(project: Project, catalogName: String): Map<String, String> {
    val libs = runCatching {
        project.rootProject.extensions.getByName(catalogName)
    }.getOrElse {
        return mapOf()
    }
    val versions = libs.javaClass.getDeclaredMethod("getVersions").invoke(libs)
    val catalog = versions.javaClass.superclass.getDeclaredField("config").run {
        isAccessible = true
        get(versions)
    }
    catalog.javaClass.getDeclaredField("versions").run {
        isAccessible = true
        val result = get(catalog) as Map<String, VersionModel>
        return result.mapValues { it.value.version.toString() }
    }
}

fun Project.project(name: String, rootPrefix: Boolean = false): Project {
    val realName = name.removePrefix(":").run {
        if(rootPrefix) {
            split(":").joinToString(":") { "${rootProject.name}-$it" }
        } else {
            this
        }
    }
    return project(":$realName")
}

fun Project.projects(vararg names: String, rootPrefix: Boolean = false): Set<Project> =
    names.mapTo(hashSetOf()) { project(it, rootPrefix) }

inline operator fun Set<Project>.invoke(block: Project.() -> Unit) {
    forEach {
        it.block()
    }
}
