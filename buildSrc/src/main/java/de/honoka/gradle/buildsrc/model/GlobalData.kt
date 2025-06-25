package de.honoka.gradle.buildsrc.model

import de.honoka.gradle.buildsrc.ext.MavenPublish
import org.gradle.api.Project

internal class GlobalData {

    companion object {

        fun refresh() {
            globalData = GlobalData()
        }
    }

    lateinit var rootProject: Project

    val mavenPublish: MavenPublish = MavenPublish()
}

internal var globalData: GlobalData = GlobalData()
