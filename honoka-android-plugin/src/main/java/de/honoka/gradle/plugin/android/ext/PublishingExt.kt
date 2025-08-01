@file:Suppress("unused")

package de.honoka.gradle.plugin.android.ext

import com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet
import de.honoka.gradle.plugin.android.util.dsl.android
import de.honoka.gradle.plugin.basic.ext.PublishingExt.PublicationsExt
import de.honoka.gradle.util.dsl.implementation
import de.honoka.gradle.util.dsl.publishing
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import java.io.File
import java.nio.file.Paths

fun PublicationsExt.defaultAar(useThisProjectName: Boolean = false) {
    project.run {
        android {
            sourceSets["main"].java {
                val sourceDirSet = if(this is DefaultAndroidSourceDirectorySet) srcDirs else setOf()
                defineAarSourcesJarTask(sourceDirSet)
            }
        }
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = group as String
                    artifactId = if(useThisProjectName) {
                        project.name
                    } else {
                        rootProject.name
                    }
                    version = project.version.toString()
                    setupAarPomDependencies(pom)
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

private fun PublicationsExt.setupAarPomDependencies(pom: MavenPom) {
    pom.withXml {
        val apiDependencies = ArrayList<String>()
        project.configurations["api"].allDependencies.forEach {
            apiDependencies.add("${it.group}:${it.name}")
        }
        asNode().appendNode("dependencies").run {
            project.configurations.implementation.configure {
                allDependencies.forEach { d ->
                    val isInvalidDependency = d.group == null ||
                        d.name.lowercase() == "unspecified" ||
                        d.version == null
                    if(isInvalidDependency) return@forEach
                    val moduleName = "${d.group}:${d.name}"
                    appendNode("dependency").run {
                        val subNodes = hashMapOf(
                            "groupId" to d.group,
                            "artifactId" to d.name,
                            "version" to d.version
                        )
                        if(!apiDependencies.contains(moduleName)) {
                            subNodes["scope"] = "runtime"
                        }
                        subNodes.forEach {
                            appendNode(it.key, it.value)
                        }
                    }
                }
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
