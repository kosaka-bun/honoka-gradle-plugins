package de.honoka.gradle.util.data

import org.gradle.api.Project

class ClassifiedProjects @PublishedApi internal constructor() {

    lateinit var jvm: Set<Project>

    lateinit var kotlin: Set<Project>

    lateinit var java8: Set<Project>

    lateinit var library: Set<Project>

    lateinit var api: Set<Project>

    lateinit var app: Set<Project>

    lateinit var businessApp: Set<Project>

    lateinit var gradlePlugin: Set<Project>

    lateinit var springBoot: Set<Project>

    lateinit var springBootApp: Set<Project>

    lateinit var android: Set<Project>

    lateinit var other: MutableMap<String, Set<Project>>
}

inline fun classifyProjects(block: ClassifiedProjects.() -> Unit): ClassifiedProjects =
    ClassifiedProjects().apply(block)
