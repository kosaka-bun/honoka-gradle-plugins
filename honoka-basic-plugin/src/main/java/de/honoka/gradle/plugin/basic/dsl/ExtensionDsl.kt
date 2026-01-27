package de.honoka.gradle.plugin.basic.dsl

import de.honoka.gradle.plugin.basic.BasicPluginExt
import de.honoka.gradle.plugin.basic.HonokaExt
import de.honoka.gradle.plugin.basic.ext.ConfigsExt
import de.honoka.gradle.plugin.basic.ext.PublishingExt
import de.honoka.gradle.util.dsl.asExt
import de.honoka.gradle.util.dsl.find
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

val Project.honoka: HonokaExt
    get() = extensions.find("honoka")

val HonokaExt.basic: BasicPluginExt
    get() = asExt().extensions.find("basic")

val BasicPluginExt.publishing: PublishingExt
    get() = asExt().extensions.find("publishing")

val Project.java: JavaPluginExtension
    get() = extensions.find("java")

fun Project.java(configure: Action<JavaPluginExtension>) {
    extensions.configure("java", configure)
}

fun Project.kapt(configure: Action<KaptExtension>) {
    extensions.configure("kapt", configure)
}

fun Project.publishing(configure: Action<PublishingExtension>) {
    extensions.configure("publishing", configure)
}

fun HonokaExt.basic(configure: Action<BasicPluginExt>) {
    asExt().extensions.configure("basic", configure)
}

fun BasicPluginExt.publishing(configure: Action<PublishingExt>) {
    asExt().extensions.configure("publishing", configure)
}

fun BasicPluginExt.configs(configure: Action<ConfigsExt>) {
    asExt().extensions.configure("configs", configure)
}
