package de.honoka.gradle.buildsrc.model

import de.honoka.gradle.buildsrc.ext.MavenPublish
import de.honoka.gradle.buildsrc.util.data.AbstractGlobalData
import de.honoka.gradle.buildsrc.util.data.AbstractGlobalDataDefinitions
import de.honoka.gradle.buildsrc.util.data.GlobalDataManager

internal class GlobalData : AbstractGlobalData() {

    val mavenPublish: MavenPublish = MavenPublish()
}

internal object GlobalDataDefinitions : AbstractGlobalDataDefinitions<GlobalData>(globalDataManager)

internal val globalDataManager = GlobalDataManager(GlobalData::class.java)
