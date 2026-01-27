package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.plugin.basic.dsl.publishing
import de.honoka.gradle.util.dsl.BaseExtension
import de.honoka.gradle.util.dsl.rawDependencies
import de.honoka.gradle.util.dsl.validVersion
import de.honoka.gradle.util.dsl.versions
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven

open class PublishingExt(val project: Project) : BaseExtension() {

    open class RepositoriesExt(val project: Project) {

        fun default() {
            project.run {
                publishing {
                    repositories {
                        mavenLocal()
                        val isReleaseVersion = version.isReleaseVersion()
                        val isDevelopmentRepository = properties["isDevelopmentRepository"]?.toString() != "false"
                        if(isReleaseVersion == isDevelopmentRepository) return@repositories
                        val remoteUrl = properties["remoteMavenRepositoryUrl"]?.toString() ?: return@repositories
                        maven(remoteUrl)
                    }
                }
            }
        }
    }

    open class PublicationsExt(val project: Project) {

        fun default() {
            project.run {
                publishing {
                    publications {
                        create<MavenPublication>("maven") {
                            groupId = group as String
                            artifactId = project.name
                            version = validVersion
                            from(components["java"])
                        }
                    }
                }
            }
        }
    }

    private lateinit var repositories: RepositoriesExt

    private lateinit var publications: PublicationsExt

    var version: String
        get() = project.version.toString()
        set(value) {
            project.version = value
            default()
        }

    var gradlePluginVersion: String
        get() = version
        set(value) {
            project.version = value
            repositories.default()
        }

    override fun defineDsls() {
        repositories = defineDsl("repositories", RepositoriesExt::class)
        publications = defineDsl("publications", PublicationsExt::class)
    }

    fun default() {
        repositories.default()
        publications.default()
    }

    fun defineCheckVersionTask() {
        project.tasks.register("checkVersionOfProjects") {
            group = "publishing"
            doLast {
                checkVersionOfProjects()
            }
        }
    }

    private fun checkVersionOfProjects() {
        val separator = "-----".repeat(7)
        val projects = arrayListOf(project.rootProject)
        project.allprojects.forEach {
            if(it.version == "unspecified") return@forEach
            projects.add(it)
        }
        val plugins = ArrayList<Pair<String, String?>>()
        val dependencies = ArrayList<Dependency>()
        val projectsPassed = run {
            var passed = true
            println("$separator\nVersions:\n")
            for(it in projects) {
                if(!passed) break
                //若project未设置version，则这里取到的version值为unspecified
                println("${it.name}=${it.version} (path: ${it.path})")
                plugins.addAll(it.plugins.versions.entries.map { it.key to it.value })
                dependencies.addAll(it.rawDependencies)
                passed = it.version.isReleaseVersion()
            }
            passed
        }
        val dependenciesPassed = if(projectsPassed) {
            val pluginInfos = HashSet<String>()
            val dependencyInfos = HashSet<String>()
            var passed = true
            println("$separator\nGradle Plugins:\n")
            for(it in plugins) {
                if(!passed) break
                val info = "${it.first}=${it.second}"
                if(info !in pluginInfos) {
                    println(info)
                    pluginInfos.add(info)
                }
                passed = it.second.isReleaseVersion()
            }
            if(passed) {
                println("$separator\nDependencies:\n")
                for(it in dependencies) {
                    if(!passed) break
                    val info = "${it.group}:${it.name}=${it.version}"
                    if(info !in dependencyInfos) {
                        println(info)
                        dependencyInfos.add(info)
                    }
                    passed = it.version.isReleaseVersion()
                }
            }
            passed
        } else {
            false
        }
        println("$separator\nResults:\n")
        println("results.projectsPassed=$projectsPassed")
        println("results.dependenciesPassed=$dependenciesPassed")
        println("results.passed=${projectsPassed && dependenciesPassed}")
        println(separator)
    }
}

//依赖的版本号可以为null，其表示某个依赖的版本号由其他配置（如：Maven BOM）确定
private fun Any?.isReleaseVersion(): Boolean = toString().lowercase().run {
    !(isEmpty() || this == "unspecified" || contains("dev"))
}
