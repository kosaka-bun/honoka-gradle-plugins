package de.honoka.gradle.plugin.android.ext

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.api.Project

open class AndroidExt(val project: Project) {

    private fun configure(configure: Action<*>) {
        project.extensions.configure("android", configure)
    }

    fun library(configure: Action<LibraryExtension>) {
        configure(configure)
    }

    fun app(configure: Action<BaseAppModuleExtension>) {
        configure(configure)
    }
}
