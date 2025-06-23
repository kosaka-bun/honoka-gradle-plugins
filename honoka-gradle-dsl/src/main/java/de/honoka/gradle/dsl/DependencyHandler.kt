package de.honoka.gradle.dsl

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dn: Any): Dependency? = run {
    add("implementation", dn)
}

fun DependencyHandler.api(dn: Any): Dependency? = run {
    add("api", dn)
}

fun DependencyHandler.kotlin(project: Project) {
    val versions = project.libVersions()
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions.getVersion("kotlin")}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${versions.getVersion("kotlin")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.getVersion("kotlin.coroutines")}")
}

fun DependencyHandler.implementationApi(dn: Any): Dependency? = run {
    implementation(dn)
    api(dn)
}
