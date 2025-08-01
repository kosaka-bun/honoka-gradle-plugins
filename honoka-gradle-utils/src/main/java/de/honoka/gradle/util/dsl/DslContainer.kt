package de.honoka.gradle.util.dsl

import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.get
import kotlin.reflect.KClass
import kotlin.reflect.jvm.isAccessible

abstract class DslContainer {

    companion object {

        internal val defineDslsActionProp = DslContainer::defineDslsAction.apply {
            isAccessible = true
        }
    }

    protected abstract val defineDslsAction: ExtensionAware.() -> Unit
}

fun <T : Any> ExtensionAware.defineDsl(name: String, clazz: KClass<T>): T {
    val extension = extensions.create(name, clazz.java)
    if(extension is DslContainer) {
        DslContainer.defineDslsActionProp.get(extension)(extension as ExtensionAware)
    }
    return extension
}

fun ExtensionAware.getNestedExtension(path: String): ExtensionAware {
    var extension: ExtensionAware = this
    path.split(".").forEach {
        extension = extension.extensions[it] as ExtensionAware
    }
    return extension
}
