package de.honoka.gradle.plugin.basic

import de.honoka.gradle.dsl.getVersion
import de.honoka.gradle.dsl.implementation
import de.honoka.gradle.dsl.libVersions
import org.gradle.api.artifacts.dsl.DependencyHandler

private val versions by lazy { BasicPlugin.rootProject.libVersions() }

private fun v(key: String) = versions.getVersion(key)

fun DependencyHandler.kotlin() {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${v("kotlin")}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${v("kotlin")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${v("kotlin.coroutines")}")
}
