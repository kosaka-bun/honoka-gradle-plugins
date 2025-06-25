package de.honoka.gradle.plugin.basic

import de.honoka.gradle.plugin.basic.model.globalDataManager
import de.honoka.gradle.util.listener.onBuildFinished
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.build.event.BuildEventsListenerRegistry
import javax.inject.Inject

@Suppress("unused")
class BasicPlugin @Inject constructor(
    private val buildEventsListenerRegistry: BuildEventsListenerRegistry
) : Plugin<Project> {

    override fun apply(project: Project) {
        globalDataManager.init(project)
        buildEventsListenerRegistry.onBuildFinished(
            project.gradle,
            "honokaBasicPlugin",
            ::onBuildFinished
        )
    }

    fun onBuildFinished() {
        globalDataManager.refresh()
    }
}
