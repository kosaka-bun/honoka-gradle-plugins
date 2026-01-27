package de.honoka.gradle.util.dsl

import org.gradle.accessors.dm.LibrariesForCommonLibs
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler
import org.gradle.api.internal.project.ProjectStateInternal
import org.gradle.kotlin.dsl.extra

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
    get() = rootProject.allprojects.first {
        (it.state as ProjectStateInternal).isConfiguring
    }

val Project.validVersion: String
    get() = version.toString().apply {
        require(isNotBlank() && this != "unspecified") {
            "Project $path has an invalid version: $this"
        }
    }

val Project.libs: LibrariesForLibs
    get() = (rootProject.extensions.find<LibrariesForLibs>("libs")).apply {
        asExt().extra.putIfNotExists(
            "honoka.commonLibs",
            rootProject.extensions.findByName("commonLibs")
        )
    }

val LibrariesForLibs.common: LibrariesForCommonLibs
    get() = asExt().extra.find("honoka.commonLibs")

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
