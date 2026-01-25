import de.honoka.gradle.buildsrc.BuildSrcPlugin
import de.honoka.gradle.buildsrc.buildSrc
import de.honoka.gradle.buildsrc.honoka
import de.honoka.gradle.buildsrc.publishing
import de.honoka.gradle.buildsrc.util.dsl.classifyProjects
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

val projects = classifyProjects {
    gradlePlugin = subprojects - projects("honoka-gradle-utils", "stubs")
}

allprojects {
    apply<BuildSrcPlugin>()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    if(project !in projects.gradlePlugin) {
        apply(plugin = "java-library")
        apply(plugin = "org.gradle.kotlin.kotlin-dsl.base")

        group = rootProject.group
    } else {
        apply(plugin = "org.gradle.kotlin.kotlin-dsl")

        group = "${rootProject.group}.plugin"
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(17)
        withSourcesJar()
    }

    dependencies {
        val stubs = project(":stubs")
        if(project.path != stubs.path) {
            compileOnly(stubs)
        }
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
