import java.nio.charset.StandardCharsets

plugins {
    java
    `maven-publish`
    `kotlin-dsl`
}

group = "de.honoka.gradle"
version = libs.versions.root.get()

//非Gradle插件项目
val notPluginProjects = listOf(project("honoka-gradle-dsl"))

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "org.gradle.kotlin.kotlin-dsl")

    if(project !in notPluginProjects) {
        apply(plugin = "java-gradle-plugin")
        group = "${rootProject.group}.plugin"
    } else {
        apply(plugin = "java-library")
        group = rootProject.group
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = sourceCompatibility
        withSourcesJar()
    }

    tasks {
        compileJava {
            options.run {
                encoding = StandardCharsets.UTF_8.name()
                val compilerArgs = compilerArgs as MutableCollection<String>
                compilerArgs += listOf("-parameters")
            }
        }

        compileKotlin {
            kotlinOptions {
                jvmTarget = java.sourceCompatibility.toString()
                freeCompilerArgs += listOf("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }
    }

    publishing {
        repositories {
            mavenLocal()
        }
    }
}
