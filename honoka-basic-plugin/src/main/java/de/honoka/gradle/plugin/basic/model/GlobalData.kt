package de.honoka.gradle.plugin.basic.model

import de.honoka.gradle.plugin.basic.ext.Dependencies
import de.honoka.gradle.plugin.basic.ext.MavenPublish
import de.honoka.gradle.util.data.AbstractGlobalData
import de.honoka.gradle.util.data.AbstractGlobalDataDefinitions
import de.honoka.gradle.util.data.GlobalDataManager

internal class GlobalData : AbstractGlobalData() {

    val dependencies: Dependencies = Dependencies()

    val mavenPublish: MavenPublish = MavenPublish()
}

internal object GlobalDataDefinitions : AbstractGlobalDataDefinitions<GlobalData>(globalDataManager)

internal val globalDataManager = GlobalDataManager(GlobalData::class.java)
