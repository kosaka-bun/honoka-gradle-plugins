@file:Suppress("unused")

package de.honoka.gradle.plugin.android.ext

import de.honoka.gradle.plugin.basic.ext.DependenciesExt
import de.honoka.gradle.util.dsl.implementation
import org.gradle.kotlin.dsl.dependencies
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible

private val vFun = DependenciesExt::class.run {
    declaredMemberFunctions.first { it.name == "v" }.apply {
        isAccessible = true
    }
}

private fun DependenciesExt.v(key: String): String = vFun.call(this, key) as String

fun DependenciesExt.kotlinAndroid() {
    project.dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${v("d.kotlin")}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${v("d.kotlin")}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${v("d.kotlin.coroutines")}")
    }
}
