package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.util.dsl.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
open class DependenciesExt(val project: Project) {

    private val versions by lazy { project.libVersions() }

    fun kotlinBom() {
        val kotlin = "org.jetbrains.kotlin:kotlin-bom:${versions["d.kotlin"]}"
        val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-bom:${versions["d.kotlin.coroutines"]}"
        project.dependencies {
            implementation(platform(kotlin))
            implementation(platform(kotlinCoroutines))
        }
    }

    fun kotlin() {
        kotlinBom()
        project.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        }
    }

    fun lombok() {
        val dn = "org.projectlombok:lombok:${versions["d.lombok"]}"
        project.dependencies {
            compileOnly(dn)
            annotationProcessor(dn)
            testCompileOnly(dn)
            testAnnotationProcessor(dn)
        }
    }

    fun springBootBom() {
        val springBoot = "org.springframework.boot:spring-boot-dependencies:${versions["d.spring.boot"]}"
        project.dependencies {
            implementation(platform(springBoot))
        }
        kotlinBom()
    }

    fun springBootConfigProcessor() {
        val configProcessor = "org.springframework.boot:spring-boot-configuration-processor:${
            versions["d.spring.boot"]
        }"
        project.dependencies {
            kapt(configProcessor)
        }
    }
}
