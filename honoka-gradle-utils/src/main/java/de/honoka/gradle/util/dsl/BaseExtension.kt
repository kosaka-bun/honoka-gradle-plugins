package de.honoka.gradle.util.dsl

import org.gradle.api.plugins.ExtensionAware
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.jvm.isAccessible

abstract class BaseExtension {

    companion object {

        internal val defineDslsFun: KFunction1<BaseExtension, Unit> =
            BaseExtension::defineDsls.apply { isAccessible = true }
    }

    protected abstract fun defineDsls()

    fun <T : Any> defineDsl(name: String, clazz: KClass<T>): T = asExt().defineDsl(name, clazz)
}

fun <T : Any> ExtensionAware.defineDsl(name: String, clazz: KClass<T>): T {
    val extension = extensions.create(name, clazz.java)
    if(extension is BaseExtension) {
        BaseExtension.defineDslsFun.call(extension)
    }
    return extension
}
