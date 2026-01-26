import de.honoka.gradle.plugin.basic.BasicPlugin
import de.honoka.gradle.plugin.basic.dsl.*
import de.honoka.gradle.util.data.classifyProjects
import de.honoka.gradle.util.dsl.applier
import de.honoka.gradle.util.dsl.projects

plugins {
    `kotlin-dsl` apply false
}

group = "de.honoka.gradle"
version = libs.versions.p.root.get()

val projects = classifyProjects {
    gradlePlugin = subprojects - projects("honoka-gradle-utils", "stubs")
}

allprojects {
    applier.clazz<BasicPlugin>()
}

subprojects {
    applier {
        java
        `maven-publish`
    }

    if(project !in projects.gradlePlugin) {
        applier {
            `java-library`
            `kotlin-dsl-base`
        }

        group = rootProject.group
    } else {
        applier.`kotlin-dsl`

        group = "${rootProject.group}.plugin"
    }

    honoka.basic {
        configs {
            java(17, true)
            kotlin()
        }
    }

    dependencies {
        val stubs = project(":stubs")
        if(project.path != stubs.path) {
            compileOnly(stubs)
        }
    }
}

honoka.basic {
    publishing {
        defineCheckVersionTask()
    }
}
