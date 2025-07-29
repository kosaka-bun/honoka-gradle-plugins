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

@Suppress("UNCHECKED_CAST")
fun <T : Any> ExtensionAware.defineDsl(name: String, clazz: KClass<T>): T {
    val instance = extensions.create(name, clazz.java)
    val extension = extensions[name] as ExtensionAware
    if(instance is DslContainer) {
        DslContainer.defineDslsActionProp.get(instance)(extension)
    }
    return instance
}

fun ExtensionAware.getNestedExtension(path: String): ExtensionAware {
    var extension: ExtensionAware = this
    path.split(".").forEach {
        extension = extension.extensions[it] as ExtensionAware
    }
    return extension
}
