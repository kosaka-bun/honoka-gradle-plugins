@file:Suppress("unused")

package de.honoka.gradle.plugin.android.ext

import de.honoka.gradle.plugin.basic.dsl.implementation
import de.honoka.gradle.plugin.basic.ext.DependenciesExt
import org.gradle.kotlin.dsl.dependencies

fun DependenciesExt.kotlinAndroid() {
    kotlinBom()
    project.dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    }
}
