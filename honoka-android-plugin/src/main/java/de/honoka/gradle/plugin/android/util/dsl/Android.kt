package de.honoka.gradle.plugin.android.util.dsl

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

fun Project.android(configure: Action<LibraryExtension>) {
    (this as ExtensionAware).extensions.configure("android", configure)
}
