@file:Suppress("unused")

package de.honoka.gradle.plugin.android.ext

import com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet
import de.honoka.gradle.plugin.android.dsl.android
import de.honoka.gradle.plugin.basic.dsl.basic
import de.honoka.gradle.plugin.basic.dsl.honoka
import de.honoka.gradle.plugin.basic.dsl.publishing
import de.honoka.gradle.plugin.basic.ext.PublishingExt
import de.honoka.gradle.plugin.basic.ext.PublishingExt.PublicationsExt
import de.honoka.gradle.plugin.basic.ext.PublishingExt.RepositoriesExt
import de.honoka.gradle.util.dsl.asExt
import de.honoka.gradle.util.dsl.category
import de.honoka.gradle.util.dsl.find
import de.honoka.gradle.util.dsl.validVersion
import org.gradle.api.internal.artifacts.dependencies.AbstractModuleDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import java.io.File
import java.nio.file.Paths

var PublishingExt.aarVersion: String
    get() = version
    set(value) {
        project.version = value
        defaultAar()
    }

var PublishingExt.useOwnProjectName: Boolean
    get() = asExt().extra.find("honoka.useOwnProjectName", false)
    set(value) {
        asExt().extra["honoka.useOwnProjectName"] = value
    }

fun PublishingExt.defaultAar() {
    asExt().extensions.run {
        find<RepositoriesExt>("repositories").default()
        find<PublicationsExt>("publications").defaultAar()
    }
}

fun PublicationsExt.defaultAar() {
    project.run {
        honoka.android.library {
            sourceSets["main"].java {
                val sourceDirSet = if(this is DefaultAndroidSourceDirectorySet) srcDirs else setOf()
                defineAarSourcesJarTask(sourceDirSet)
            }
        }
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = group as String
                    artifactId = if(honoka.basic.publishing.useOwnProjectName) {
                        project.name
                    } else {
                        rootProject.name
                    }
                    version = validVersion
                    setupAarPom(pom)
                    afterEvaluate {
                        val artifacts = listOf(
                            tasks["bundleReleaseAar"],
                            tasks["sourcesJar"]
                        )
                        setArtifacts(artifacts)
                    }
                }
            }
        }
    }
}

private fun PublicationsExt.setupAarPom(pom: MavenPom) {
    pom.withXml {
        val dependenciesNode = asNode().appendNode("dependencies")
        val dependencies = HashSet<String>()
        listOf("api", "implementation", "runtimeOnly").forEach { configName ->
            project.configurations[configName].allDependencies.forEach { d ->
                val dependencyId = "${d.group}:${d.name}"
                val isInvalidDependency = d.group == null || d.name.lowercase() == "unspecified"
                if(dependencyId in dependencies || isInvalidDependency) return@forEach
                if(d is DefaultExternalModuleDependency) {
                    if(d.category == "platform") return@forEach
                }
                dependenciesNode.appendNode("dependency").run {
                    val subNodes = hashMapOf(
                        "groupId" to d.group,
                        "artifactId" to d.name
                    )
                    d.version?.let {
                        subNodes["version"] = it
                    }
                    if(configName != "api") {
                        subNodes["scope"] = "runtime"
                    }
                    subNodes.forEach {
                        appendNode(it.key, it.value)
                    }
                    if(d !is AbstractModuleDependency || d.excludeRules.isEmpty()) return@run
                    appendNode("exclusions").run {
                        d.excludeRules.forEach {
                            appendNode("exclusion").run {
                                appendNode("groupId", it.group)
                                appendNode("artifactId", it.module)
                            }
                        }
                    }
                }
                dependencies.add(dependencyId)
            }
        }
    }
}

private fun PublicationsExt.defineAarSourcesJarTask(sourceDirSet: Set<File>) {
    project.run {
        tasks.register<Jar>("sourcesJar") {
            group = "build"
            val buildDirPath = layout.buildDirectory.get().asFile.absolutePath
            destinationDirectory.set(Paths.get(buildDirPath, "libs").toFile())
            archiveFileName.set("${project.name}-$version-sources.jar")
            archiveClassifier.set("sources")
            from(*sourceDirSet.toTypedArray())
        }
    }
}
