package de.honoka.gradle.plugin.basic.model

import de.honoka.gradle.plugin.basic.ext.Dependencies
import de.honoka.gradle.plugin.basic.ext.MavenPublish
import org.gradle.api.Project

internal class GlobalData {

    lateinit var rootProject: Project

    val dependencies: Dependencies = Dependencies()

    val mavenPublish: MavenPublish = MavenPublish()
}

internal lateinit var globalData: GlobalData
