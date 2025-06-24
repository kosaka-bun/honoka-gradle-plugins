package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.dsl.publishing
import de.honoka.gradle.dsl.rawDependencies
import de.honoka.gradle.plugin.basic.model.globalData
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven

internal class MavenPublish {

    val projectsWillPublish = ArrayList<Project>()
}

class MavenPublishDsl(private val project: Project) {

    var version: String
        get() = project.version.toString()
        set(value) = setupVersionAndPublishing(project, value)

    fun defineCheckVersionTask() {
        project.tasks.register("checkVersionOfProjects") {
            group = "publishing"
            doLast {
                checkVersionOfProjects()
            }
        }
    }
}

private fun setupVersionAndPublishing(project: Project, version: String) {
    project.run {
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
    }
    globalData.mavenPublish.projectsWillPublish.add(project)
}

private fun checkVersionOfProjects() {
    val separator = "-----".repeat(7)
    val projects = globalData.mavenPublish.projectsWillPublish + globalData.rootProject
    val dependencies = HashSet<Dependency>()
    val projectsPassed = run {
        var passed = true
        println("$separator\nVersions:\n")
        for(it in projects) {
            if(!passed) break
            //若project未设置version，则这里取到的version值为unspecified
            println("${it.name}=${it.version}")
            dependencies.addAll(it.rawDependencies)
            passed = it.version.isReleaseVersion()
        }
        passed
    }
    val dependenciesPassed = run {
        var passed = true
        println("$separator\nDependencies:\n")
        for(it in dependencies) {
            if(!passed) break
            println("${it.group}:${it.name}=${it.version}")
            passed = it.version.isReleaseVersion()
        }
        passed
    }
    println("$separator\nResults:\n")
    println("results.projectsPassed=$projectsPassed")
    println("results.dependenciesPassed=$dependenciesPassed")
    println("results.passed=${projectsPassed && dependenciesPassed}")
    println(separator)
}

private fun Any?.isReleaseVersion(): Boolean = toString().lowercase().run {
    !(isEmpty() || this == "unspecified" || contains("dev"))
}
