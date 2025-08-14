@file:Suppress("UnstableApiUsage")

pluginManagement {
    val customRepositories: RepositoryHandler.() -> Unit = {
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
        google()
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
        mavenLocal()
        maven("https://mirrors.honoka.de/maven-repo/release")
        maven("https://mirrors.honoka.de/maven-repo/development")
    }
    repositories(customRepositories)
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories(customRepositories)
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "honoka-gradle-plugins"

include("honoka-gradle-utils")
include("honoka-basic-plugin")
include("honoka-android-plugin")
