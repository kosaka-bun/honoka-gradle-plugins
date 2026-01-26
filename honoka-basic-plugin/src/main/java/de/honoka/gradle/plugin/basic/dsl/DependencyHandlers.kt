package de.honoka.gradle.plugin.basic.dsl

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.closureOf

private typealias Config = ModuleDependency.() -> Unit

private fun DependencyHandler.add(scope: String, dn: Any, config: Config? = null): Dependency? {
    val closure = config?.let { closureOf(it) }
    return if(closure != null) {
        add(scope, dn, closure)
    } else {
        add(scope, dn)
    }
}

fun DependencyHandler.implementation(dn: Any, config: Config? = null): Dependency? =
    add("implementation", dn, config)

fun DependencyHandler.api(dn: Any, config: Config? = null): Dependency? =
    add("api", dn, config)

fun DependencyHandler.compileOnly(dn: Any, config: Config? = null): Dependency? =
    add("compileOnly", dn, config)

fun DependencyHandler.annotationProcessor(dn: Any, config: Config? = null): Dependency? =
    add("annotationProcessor", dn, config)

fun DependencyHandler.testImplementation(dn: Any, config: Config? = null): Dependency? =
    add("testImplementation", dn, config)

fun DependencyHandler.testCompileOnly(dn: Any, config: Config? = null): Dependency? =
    add("testCompileOnly", dn, config)

fun DependencyHandler.testAnnotationProcessor(dn: Any, config: Config? = null): Dependency? =
    add("testAnnotationProcessor", dn, config)

fun DependencyHandler.kapt(dn: Any, config: Config? = null): Dependency? =
    add("kapt", dn, config)
