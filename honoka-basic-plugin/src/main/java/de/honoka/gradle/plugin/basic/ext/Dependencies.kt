package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.dsl.getVersion
import de.honoka.gradle.dsl.implementation
import de.honoka.gradle.dsl.libVersions
import de.honoka.gradle.plugin.basic.model.globalData
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class Dependencies {

    val versions by lazy { globalData.rootProject.libVersions() }
}

object DependenciesStaticDsl {

    private fun v(key: String) = globalData.dependencies.versions.getVersion(key)

    fun DependencyHandler.kotlin() {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${v("kotlin")}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${v("kotlin")}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${v("kotlin.coroutines")}")
    }
}
