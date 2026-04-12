package de.honoka.gradle.plugin.spring.dsl

import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.tasks.bundling.BootJar

val TaskContainer.bootJar: TaskProvider<BootJar>
    get() = named<BootJar>("bootJar")
