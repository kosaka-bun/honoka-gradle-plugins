package de.honoka.gradle.buildsrc.util.dsl

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler
import org.gradle.api.publish.PublishingExtension

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

fun Project.publishing(configure: Action<PublishingExtension>) {
    extensions.configure("publishing", configure)
}
