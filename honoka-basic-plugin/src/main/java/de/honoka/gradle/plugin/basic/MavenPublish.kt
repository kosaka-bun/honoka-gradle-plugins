package de.honoka.gradle.plugin.basic

import de.honoka.gradle.dsl.publishing
import de.honoka.gradle.dsl.rawDependencies
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven

private val projectsWillPublish = ArrayList<Project>()

private fun checkVersionOfProjects() {
    var projectsPassed = true
    val dependencies = HashSet<Dependency>()
    val separator = "-----".repeat(7)
    println("$separator\nVersions:")
    listOf(BasicPlugin.rootProject, *projectsWillPublish.toTypedArray()).forEach {
        if(!projectsPassed) return@forEach
        //若project未设置version，则这里取到的version值为unspecified
        println("${it.name}=${it.version}")
        dependencies.addAll(it.rawDependencies)
        projectsPassed = it.version.isReleaseVersion()
    }
    val dependenciesPassed = run {
        var passed = true
        println("$separator\nDependencies:")
        for(it in dependencies) {
            if(!passed) break
            println("${it.group}:${it.name}=${it.version}")
            passed = it.version.isReleaseVersion()
        }
        passed
    }
    println("$separator\nResults:")
    println("results.projectsPassed=$projectsPassed")
    println("results.dependenciesPassed=$dependenciesPassed")
    println("results.passed=${projectsPassed && dependenciesPassed}")
    println(separator)
}

private fun Any?.isReleaseVersion(): Boolean = toString().lowercase().run {
    !(isEmpty() || this == "unspecified" || contains("dev"))
}

fun Project.setupVersionAndPublishing(version: String) {
    this.version = version
    publishing {
        repositories {
            val isReleaseVersion = version.isReleaseVersion()
            val isDevelopmentRepository = properties["isDevelopmentRepository"]?.toString() == "true"
            if(isReleaseVersion == isDevelopmentRepository) return@repositories
            val remoteUrl = properties["remoteMavenRepositoryUrl"]?.toString() ?: return@repositories
            maven(remoteUrl)
        }
        publications {
            create<MavenPublication>("maven") {
                groupId = group as String
                artifactId = project.name
                this.version = version
                from(components["java"])
            }
        }
    }
    projectsWillPublish.add(this)
}

fun Project.defineCheckVersionOfProjectsTask() {
    tasks.register("checkVersionOfProjects") {
        group = "publishing"
        doLast {
            checkVersionOfProjects()
        }
    }
}
