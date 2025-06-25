package de.honoka.gradle.buildsrc.util.project

import org.gradle.api.Project

object CurrentProject {

    fun enable(project: Project) {
        realCurrentProject.run {
            get() ?: set(project)
            project.run {
                beforeEvaluate {
                    set(project)
                }
                afterEvaluate {
                    remove()
                }
            }
        }
    }
}

private val realCurrentProject = ThreadLocal<Project>()

val currentProject: Project
    get() = realCurrentProject.get()
