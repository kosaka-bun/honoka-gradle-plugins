@file:Suppress("UNCHECKED_CAST")

package de.honoka.gradle.util.dsl

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get

fun Any.asExt(): ExtensionAware = this as ExtensionAware

fun <T> ExtensionContainer.find(name: String, defaultValue: T? = null): T =
    (findByName(name) ?: defaultValue) as T

fun <T> ExtraPropertiesExtension.find(key: String, defaultValue: T? = null): T =
    (if(has(key)) get(key) else defaultValue) as T

fun ExtraPropertiesExtension.putIfNotExists(key: String, value: Any?) {
    if(has(key)) return
    this[key] = value
}

var ExtensionAware.project: Project
    get() = when(this) {
        is Project -> project
        else -> extra.find("honoka.project")
    }
    set(value) {
        extra["honoka.project"] = value
    }

fun <T : Any> ExtensionAware.getNestedExtension(path: String): T {
    var extension: ExtensionAware = this
    path.split(".").forEach {
        extension = extension.extensions[it].asExt()
    }
    return extension as T
}
