package de.honoka.gradle.util.dsl

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.internal.catalog.VersionModel
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.named
import java.io.File
import java.util.jar.JarFile
import org.gradle.api.artifacts.Configuration as ArtifactsConfiguration

val PluginContainer.versions: Map<String, String?>
    get() {
        val map = HashMap<String, String?>()
        forEach { p ->
            val jarUrl = p.javaClass.protectionDomain.codeSource.location
            val jarFile = File(jarUrl.toURI()).run {
                if(!exists()) return@forEach
                JarFile(this)
            }
            val idList = run {
                val prefix = "META-INF/gradle-plugins/"
                val suffix = ".properties"
                jarFile.entries().asSequence().filter {
                    it.name.startsWith(prefix) && it.name.endsWith(suffix)
                }.map {
                    it.name.removePrefix(prefix).removeSuffix(suffix)
                }.filter {
                    runCatching {
                        hasPlugin(it)
                    }.getOrDefault(false)
                }
            }
            val fileName = jarUrl.path.lowercase().run {
                split('/').last().removeSuffix(".jar")
            }
            val version = fileName.split('-').run {
                val index = indexOfFirst { it.contains('.') }
                if(index > 0) subList(index, size).joinToString("-") else null
            }
            idList.forEach {
                map[it] = version
            }
        }
        return map
    }

fun Map<String, VersionModel>.getVersion(key: String): String = get(key)?.version.toString()

val NamedDomainObjectContainer<ArtifactsConfiguration>.implementation
    get() = named<ArtifactsConfiguration>("implementation")
