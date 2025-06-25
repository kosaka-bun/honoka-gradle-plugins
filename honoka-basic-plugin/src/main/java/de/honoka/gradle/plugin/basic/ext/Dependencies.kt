package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.plugin.basic.model.globalData
import de.honoka.gradle.util.dsl.getVersion
import de.honoka.gradle.util.dsl.implementation
import de.honoka.gradle.util.dsl.libVersions
import de.honoka.gradle.util.project.currentProject
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class Dependencies {

    val versions by lazy { currentProject.libVersions() }
}

object DependenciesDsl {

    private fun v(key: String) = globalData.dependencies.versions.getVersion(key)

    fun DependencyHandler.kotlin() {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${v("kotlin")}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${v("kotlin")}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${v("kotlin.coroutines")}")
    }
}
