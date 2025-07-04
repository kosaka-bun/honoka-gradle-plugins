package de.honoka.gradle.buildsrc.ext

import de.honoka.gradle.buildsrc.model.GlobalDataDefinitions.currentProject
import de.honoka.gradle.buildsrc.model.GlobalDataDefinitions.globalData
import de.honoka.gradle.buildsrc.model.GlobalDataDefinitions.globalDataOfRoot
import de.honoka.gradle.buildsrc.util.dsl.publishing
import de.honoka.gradle.buildsrc.util.dsl.rawDependencies
import de.honoka.gradle.buildsrc.util.dsl.versions
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven

internal class MavenPublish {

    val projectsWillPublish = ArrayList<Project>()
}

@Suppress("UnusedReceiverParameter")
object MavenPublishDsl {

    fun RepositoryHandler.default() {
        setupDefaultRepositories(currentProject)
    }

    fun PublicationContainer.default() {
        currentProject.let {
            setupDefaultRepositories(it)
            setupDefaultPublication(it)
        }
    }

    fun PublishingExtension.defineCheckVersionTask() {
        currentProject.tasks.register("checkVersionOfProjects") {
            group = "publishing"
            doLast {
                checkVersionOfProjects(project)
            }
        }
    }
}

private fun setupDefaultRepositories(project: Project) {
    project.run {
        publishing {
            repositories {
                val isReleaseVersion = version.isReleaseVersion()
                val isDevelopmentRepository = properties["isDevelopmentRepository"]?.toString() == "true"
                if(isReleaseVersion == isDevelopmentRepository) return@repositories
                val remoteUrl = properties["remoteMavenRepositoryUrl"]?.toString() ?: return@repositories
                maven(remoteUrl)
            }
        }
    }
}

private fun setupDefaultPublication(project: Project) {
    project.run {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = group as String
                    artifactId = project.name
                    version = project.version.toString()
                    from(components["java"])
                }
            }
        }
    }
    globalData.mavenPublish.projectsWillPublish.add(project)
}

private fun checkVersionOfProjects(project: Project) {
    val globalData = project.globalDataOfRoot
    val separator = "-----".repeat(7)
    val projects = listOf(globalData.rootProject) + globalData.mavenPublish.projectsWillPublish
    val plugins = ArrayList<Pair<String, String?>>()
    val dependencies = HashSet<Dependency>()
    val projectsPassed = run {
        var passed = true
        println("$separator\nVersions:\n")
        for(it in projects) {
            if(!passed) break
            //若project未设置version，则这里取到的version值为unspecified
            println("${it.name}=${it.version}")
            plugins.addAll(it.plugins.versions.entries.map { it.key to it.value })
            dependencies.addAll(it.rawDependencies)
            passed = it.version.isReleaseVersion()
        }
        passed
    }
    val dependenciesPassed = run {
        var passed = true
        println("$separator\nGradle Plugins:\n")
        for(it in plugins) {
            if(!passed) break
            println("${it.first}=${it.second}")
            passed = it.first.isReleaseVersion()
        }
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

//依赖的版本号可以为null，其表示某个依赖的版本号由其他配置（如：Maven BOM）确定
private fun Any?.isReleaseVersion(): Boolean = toString().lowercase().run {
    !(isEmpty() || this == "unspecified" || contains("dev"))
}
