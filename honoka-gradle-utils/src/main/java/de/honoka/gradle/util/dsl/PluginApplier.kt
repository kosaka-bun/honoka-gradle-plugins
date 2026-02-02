package de.honoka.gradle.util.dsl

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.declarativedsl.plugins.MutablePluginDependencySpec
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

class PluginApplier internal constructor(@PublishedApi internal val project: Project) : AbstractPluginApplier {

    override fun id(id: String): PluginDependencySpec {
        project.applyPlugin(id)
        return MutablePluginDependencySpec("")
    }

    inline fun <reified T : Plugin<Project>> clazz() {
        project.apply(mapOf("plugin" to T::class.java))
    }
}

@Suppress("PropertyName", "unused")
private interface AbstractPluginApplier : PluginDependenciesSpec {

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

val Project.applier: PluginApplier
    get() = PluginApplier(this)

inline fun Project.applier(block: PluginApplier.() -> Unit) {
    applier.run(block)
}

fun Project.applyPlugin(id: String) {
    apply(mapOf("plugin" to id))
}
