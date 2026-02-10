package de.honoka.gradle.plugin.android

import de.honoka.gradle.plugin.android.ext.AndroidExt
import de.honoka.gradle.plugin.basic.dsl.honoka
import de.honoka.gradle.util.dsl.applier
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            applier.`honoka-basic`
            honoka.defineDsl("android", AndroidExt::class)
        }
    }
}
