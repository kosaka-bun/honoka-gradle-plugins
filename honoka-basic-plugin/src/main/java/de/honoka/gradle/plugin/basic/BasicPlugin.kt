package de.honoka.gradle.plugin.basic

import de.honoka.gradle.plugin.basic.model.GlobalData
import de.honoka.gradle.plugin.basic.model.globalData
import de.honoka.gradle.util.listener.onBuildFinished
import de.honoka.gradle.util.project.CurrentProject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.build.event.BuildEventsListenerRegistry
import javax.inject.Inject

class BasicPlugin @Inject constructor(
    private val buildEventsListenerRegistry: BuildEventsListenerRegistry
) : Plugin<Project> {

    override fun apply(project: Project) {
        globalData.rootProject = project.rootProject
        CurrentProject.enable(project)
        buildEventsListenerRegistry.onBuildFinished(
            project.gradle,
            "honokaBasicPlugin",
            ::onBuildFinished
        )
    }

    fun onBuildFinished() {
        GlobalData.refresh()
    }
}
