package de.honoka.gradle.plugin.spring

import de.honoka.gradle.plugin.basic.dsl.honoka
import de.honoka.gradle.plugin.spring.ext.SpringExt
import de.honoka.gradle.util.dsl.applier
import org.gradle.api.Plugin
import org.gradle.api.Project

class SpringPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            applier.`honoka-basic`
            honoka.defineDsl("spring", SpringExt::class)
        }
    }
}
