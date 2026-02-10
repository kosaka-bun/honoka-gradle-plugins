package de.honoka.gradle.util.dsl

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

@Suppress("PropertyName", "unused")
internal interface CommonPluginsSpec : PluginDependenciesSpec {

    val `spring-boot`: PluginDependencySpec
        get() = id("org.springframework.boot")

    val `android-library`: PluginDependencySpec
        get() = id("com.android.library")

    val `android-application`: PluginDependencySpec
        get() = id("com.android.application")

    val kotlin: PluginDependencySpec
        get() = id("org.jetbrains.kotlin.jvm")

    val `kotlin-kapt`: PluginDependencySpec
        get() = id("org.jetbrains.kotlin.kapt")

    val `kotlin-android`: PluginDependencySpec
        get() = id("org.jetbrains.kotlin.android")

    val `kotlin-allopen`: PluginDependencySpec
        get() = id("org.jetbrains.kotlin.plugin.allopen")

    val `kotlin-lombok`: PluginDependencySpec
        get() = id("org.jetbrains.kotlin.plugin.lombok")

    val `kotlin-spring`: PluginDependencySpec
        get() = id("org.jetbrains.kotlin.plugin.spring")

    val `honoka-basic`: PluginDependencySpec
        get() = id("de.honoka.gradle.plugin.basic")

    val `honoka-android`: PluginDependencySpec
        get() = id("de.honoka.gradle.plugin.android")
}
