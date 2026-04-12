package de.honoka.gradle.plugin.spring.ext

import de.honoka.gradle.plugin.spring.dsl.bootJar
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.invoke

open class BootJarExt(val project: Project) {

    fun withProjectName() {
        project.tasks.bootJar {
            archiveFileName = "${project.name}.jar"
        }
    }
}
