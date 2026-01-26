package de.honoka.gradle.plugin.android.dsl

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project

fun Project.android(configure: Action<LibraryExtension>) {
    extensions.configure("android", configure)
}
