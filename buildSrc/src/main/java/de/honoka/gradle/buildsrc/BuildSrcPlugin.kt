package de.honoka.gradle.buildsrc

import de.honoka.gradle.buildsrc.model.GlobalData
import de.honoka.gradle.buildsrc.model.globalData
import de.honoka.gradle.buildsrc.util.listener.onBuildFinished
import de.honoka.gradle.buildsrc.util.project.CurrentProject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.build.event.BuildEventsListenerRegistry
import javax.inject.Inject

class BuildSrcPlugin @Inject constructor(
    private val buildEventsListenerRegistry: BuildEventsListenerRegistry
) : Plugin<Project> {

    override fun apply(project: Project) {
        globalData.rootProject = project.rootProject
        CurrentProject.enable(project)
        buildEventsListenerRegistry.onBuildFinished(
            project.gradle,
            "buildSrcPlugin",
            ::onBuildFinished
        )
    }

    fun onBuildFinished() {
        GlobalData.refresh()
    }
}
