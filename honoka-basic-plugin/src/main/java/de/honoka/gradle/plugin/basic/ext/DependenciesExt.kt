package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.util.dsl.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
open class DependenciesExt(private val project: Project) {

    private val versions by lazy { project.libVersions() }

    private fun v(key: String) = versions.getVersion(key)

    fun kotlin() {
        project.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${v("d.kotlin")}")
            implementation("org.jetbrains.kotlin:kotlin-reflect:${v("d.kotlin")}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${v("d.kotlin.coroutines")}")
        }
    }

    fun lombok() {
        val dn = "org.projectlombok:lombok:${v("d.lombok")}"
        project.dependencies {
            compileOnly(dn)
            annotationProcessor(dn)
            testCompileOnly(dn)
            testAnnotationProcessor(dn)
        }
    }
}
