package de.honoka.gradle.util.dsl

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dn: Any): Dependency? = run {
    add("implementation", dn)
}

fun DependencyHandler.api(dn: Any): Dependency? = run {
    add("api", dn)
}

fun DependencyHandler.compileOnly(dn: Any): Dependency? = run {
    add("compileOnly", dn)
}

fun DependencyHandler.annotationProcessor(dn: Any): Dependency? = run {
    add("annotationProcessor", dn)
}

fun DependencyHandler.testCompileOnly(dn: Any): Dependency? = run {
    add("testCompileOnly", dn)
}

fun DependencyHandler.testAnnotationProcessor(dn: Any): Dependency? = run {
    add("testAnnotationProcessor", dn)
}
