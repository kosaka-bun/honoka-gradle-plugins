package de.honoka.gradle.plugin.android.dsl

import de.honoka.gradle.plugin.basic.dsl.DependencyConfig
import de.honoka.gradle.plugin.basic.dsl.add
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidTestImplementation(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("androidTestImplementation", dn, config)
