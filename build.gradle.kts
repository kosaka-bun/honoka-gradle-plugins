import de.honoka.gradle.buildsrc.BuildSrcPlugin
import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.util.dsl.projects
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.charset.StandardCharsets

plugins {
    java
    `maven-publish`
    `kotlin-dsl-base`
}

group = "de.honoka.gradle"
version = libs.versions.p.root.get()

//非Gradle插件项目
val notPluginProjects = projects("honoka-gradle-utils")

//非Java 8项目
val notJava8Projects = projects("honoka-android-plugin")

allprojects {
    apply<BuildSrcPlugin>()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    if(project in notPluginProjects) {
        apply(plugin = "java-library")
        apply(plugin = "org.gradle.kotlin.kotlin-dsl.base")

        group = rootProject.group
    } else {
        apply(plugin = "org.gradle.kotlin.kotlin-dsl")

        group = "${rootProject.group}.plugin"
    }

    java {
        if(project !in notJava8Projects) {
            toolchain.languageVersion = JavaLanguageVersion.of(8)
        }
        withSourcesJar()
    }

    tasks {
        withType<JavaCompile> {
            options.run {
                encoding = StandardCharsets.UTF_8.name()
                val compilerArgs = compilerArgs as MutableCollection<String>
                compilerArgs += listOf("-parameters")
            }
        }

        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}

honoka.buildSrc {
    publishing {
        defineCheckVersionTask()
    }
}
