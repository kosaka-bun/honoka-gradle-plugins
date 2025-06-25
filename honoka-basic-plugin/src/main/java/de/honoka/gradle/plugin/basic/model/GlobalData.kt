package de.honoka.gradle.plugin.basic.model

import de.honoka.gradle.plugin.basic.ext.Dependencies
import de.honoka.gradle.plugin.basic.ext.MavenPublish
import org.gradle.api.Project

internal class GlobalData {

    companion object {

        fun refresh() {
            globalData = GlobalData()
        }
    }

    lateinit var rootProject: Project

    val dependencies: Dependencies = Dependencies()

    val mavenPublish: MavenPublish = MavenPublish()
}

internal var globalData: GlobalData = GlobalData()
