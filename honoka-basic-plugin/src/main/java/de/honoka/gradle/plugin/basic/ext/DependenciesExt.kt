package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.plugin.basic.dsl.*
import org.gradle.api.Project
import org.gradle.api.internal.catalog.VersionModel
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

open class DependenciesExt(val project: Project) {

    private val versions by lazy { parseVersions() }

    @Suppress("UNCHECKED_CAST")
    private fun parseVersions(): Map<String, String> {
        val result = HashMap<String, String>()
        //靠后的版本信息将覆盖靠前的同名版本信息
        val catalogNames = listOf("commonLibs", "libs")
        catalogNames.forEach {
            val libs = runCatching {
                project.rootProject.extensions.getByName(it)
            }.getOrElse {
                return@forEach
            }
            val versions = libs.javaClass.getDeclaredMethod("getVersions").invoke(libs)
            val catalog = versions.javaClass.superclass.getDeclaredField("config").run {
                isAccessible = true
                get(versions)
            }
            val versionModels = catalog.javaClass.getDeclaredField("versions").run {
                isAccessible = true
                get(catalog) as Map<String, VersionModel>
            }
            result.putAll(versionModels.mapValues { e -> e.value.version.toString() })
        }
        return result
    }

    fun kotlinBom() {
        val boms = listOf(
            "org.jetbrains.kotlin:kotlin-bom:${versions["d.kotlin"]}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-bom:${versions["d.kotlin.coroutines"]}"
        )
        project.dependencies {
            boms.forEach {
                implementation(platform(it))
            }
        }
    }

    fun kotlin() {
        kotlinBom()
        project.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
            testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
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
        val dn = "org.springframework.boot:spring-boot-dependencies:${versions["d.spring.boot"]}"
        project.dependencies {
            implementation(platform(dn)) {
                exclude("org.jetbrains.kotlin")
                exclude("org.jetbrains.kotlinx")
            }
        }
    }

    fun springBootConfigProcessor() {
        val dn = "org.springframework.boot:spring-boot-configuration-processor:${
            versions["d.spring.boot"]
        }"
        project.dependencies {
            kapt(dn)
        }
    }
}
