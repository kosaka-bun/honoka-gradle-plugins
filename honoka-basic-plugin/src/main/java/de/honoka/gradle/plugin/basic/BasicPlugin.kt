package de.honoka.gradle.plugin.basic

import org.gradle.api.Plugin
import org.gradle.api.Project

class BasicPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("basicPluginTest") {
            doLast {
                println("hello")
            }
        }
    }
}
