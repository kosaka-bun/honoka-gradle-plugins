package de.honoka.gradle.plugin.basic.dsl

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.closureOf

typealias DependencyConfig = ModuleDependency.() -> Unit

fun DependencyHandler.add(scope: String, dn: Any, config: DependencyConfig? = null): Dependency? {
    val closure = config?.let { closureOf(it) }
    return if(closure != null) {
        add(scope, dn, closure)
    } else {
        add(scope, dn)
    }
}

fun DependencyHandler.implementation(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("implementation", dn, config)

fun DependencyHandler.api(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("api", dn, config)

fun DependencyHandler.compileOnly(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("compileOnly", dn, config)

fun DependencyHandler.annotationProcessor(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("annotationProcessor", dn, config)

fun DependencyHandler.testImplementation(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("testImplementation", dn, config)

fun DependencyHandler.testCompileOnly(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("testCompileOnly", dn, config)

fun DependencyHandler.testAnnotationProcessor(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("testAnnotationProcessor", dn, config)

fun DependencyHandler.kapt(dn: Any, config: DependencyConfig? = null): Dependency? =
    add("kapt", dn, config)
