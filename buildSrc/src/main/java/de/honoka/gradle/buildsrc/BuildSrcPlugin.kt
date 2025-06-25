package de.honoka.gradle.buildsrc

import de.honoka.gradle.buildsrc.model.globalDataManager
import de.honoka.gradle.buildsrc.util.listener.onBuildFinished
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.build.event.BuildEventsListenerRegistry
import javax.inject.Inject

class BuildSrcPlugin @Inject constructor(
    private val buildEventsListenerRegistry: BuildEventsListenerRegistry
) : Plugin<Project> {

    override fun apply(project: Project) {
        globalDataManager.init(project)
        buildEventsListenerRegistry.onBuildFinished(
            project.gradle,
            "buildSrcPlugin",
            ::onBuildFinished
        )
    }

    fun onBuildFinished() {
        globalDataManager.refresh()
    }
}
