package de.honoka.gradle.plugin.basic.dsl

import de.honoka.gradle.plugin.basic.BasicPluginExt
import de.honoka.gradle.plugin.basic.HonokaExt
import de.honoka.gradle.plugin.basic.ext.ConfigsExt
import de.honoka.gradle.plugin.basic.ext.PublishingExt
import de.honoka.gradle.util.dsl.asExt
import org.gradle.accessors.dm.LibrariesForCommonLibs
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.extra
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

val Project.libs: LibrariesForLibs
    get() = (rootProject.extensions.getByName("libs") as LibrariesForLibs).apply {
        this as ExtensionAware
        if(extra.has("commonLibs")) return@apply
        extra["commonLibs"] = rootProject.extensions.findByName("commonLibs")
    }

val LibrariesForLibs.common: LibrariesForCommonLibs
    get() = asExt().extra["commonLibs"] as LibrariesForCommonLibs

val Project.honoka: HonokaExt
    get() = extensions.getByName("honoka") as HonokaExt

val Project.java: JavaPluginExtension
    get() = extensions.getByName("java") as JavaPluginExtension

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
