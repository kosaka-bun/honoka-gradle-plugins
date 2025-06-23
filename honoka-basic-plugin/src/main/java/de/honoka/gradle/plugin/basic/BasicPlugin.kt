package de.honoka.gradle.plugin.basic

import org.gradle.api.Plugin
import org.gradle.api.Project

class BasicPlugin : Plugin<Project> {

    companion object {

        lateinit var rootProject: Project
    }

    override fun apply(project: Project) {
        rootProject = project.rootProject
        project.tasks.register("basicPluginTest") {
            doLast {
                println("hello")
            }
        }
    }
}
