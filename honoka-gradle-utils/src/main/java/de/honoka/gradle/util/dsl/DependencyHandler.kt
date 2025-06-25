package de.honoka.gradle.util.dsl

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dn: Any): Dependency? = run {
    add("implementation", dn)
}

fun DependencyHandler.api(dn: Any): Dependency? = run {
    add("api", dn)
}

fun DependencyHandler.implementationApi(dn: Any): Dependency? = run {
    implementation(dn)
    api(dn)
}
