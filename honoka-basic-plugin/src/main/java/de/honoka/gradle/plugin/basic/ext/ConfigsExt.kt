package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.plugin.basic.dsl.allOpen
import de.honoka.gradle.plugin.basic.dsl.java
import de.honoka.gradle.plugin.basic.dsl.kapt
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.charset.StandardCharsets

open class ConfigsExt(val project: Project) {

    fun java(version: Int, withSourcesJar: Boolean = false) {
        project.java {
            toolchain.languageVersion.set(JavaLanguageVersion.of(version))
            if(withSourcesJar) {
                withSourcesJar()
            }
        }
        javaTask()
    }

    fun javaTask() {
        project.tasks.withType<JavaCompile> {
            options.run {
                encoding = StandardCharsets.UTF_8.name()
                val compilerArgs = compilerArgs as MutableCollection<String>
                compilerArgs += listOf("-parameters")
            }
        }
        test()
    }

    fun kotlin() {
        configureKotlin(project)
    }

    fun kapt() {
        project.kapt {
            keepJavacAnnotationProcessors = true
        }
    }

    fun allOpen() {
        project.allOpen {
            annotation("de.honoka.sdk.util.kotlin.various.AllOpen")
        }
    }

    fun test() {
        project.tasks.withType<Test> {
            useJUnitPlatform()
            workingDir = project.rootDir
        }
    }
}

private fun configureKotlin(project: Project) {
    /*
     * 由于除了原本的compileKotlin任务外，还存在compileTestKotlin和kapt的KaptGenerateStubsTask
     * （KotlinCompile的子类）任务需要配置，因此这里不能使用“compileKotlin {}”块。
     */
    project.tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
        }
    }
}
