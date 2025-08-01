package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.util.dsl.*
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven

@Suppress("unused")
open class PublishingExt(val project: Project) : DslContainer() {

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
                            version = project.version.toString()
                            from(components["java"])
                        }
                    }
                }
            }
        }
    }

    override val defineDslsAction: ExtensionAware.() -> Unit = {
        repositories = defineDsl("repositories", RepositoriesExt::class)
        publications = defineDsl("publications", PublicationsExt::class)
    }

    private lateinit var repositories: RepositoriesExt

    private lateinit var publications: PublicationsExt

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
            //有些项目可能没有导入maven-publish插件，那么publishing函数就无法执行成功
            runCatching {
                it.publishing {
                    if(publications.isNotEmpty()) {
                        projects.add(it)
                    }
                }
            }
        }
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
                passed = it.second.isReleaseVersion()
            }
            if(passed) {
                println("$separator\nDependencies:\n")
                for(it in dependencies) {
                    if(!passed) break
                    println("${it.group}:${it.name}=${it.version}")
                    passed = it.version.isReleaseVersion()
                }
            }
            passed
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
