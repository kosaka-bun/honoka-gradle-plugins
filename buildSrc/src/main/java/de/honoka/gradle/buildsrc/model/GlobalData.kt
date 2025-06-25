package de.honoka.gradle.buildsrc.model

import de.honoka.gradle.buildsrc.ext.MavenPublish
import de.honoka.gradle.buildsrc.util.dsl.currentProject
import org.gradle.api.Project
import java.util.concurrent.ConcurrentHashMap

internal class GlobalData {

    companion object {

        fun init(project: Project) {
            val rootProject = project.rootProject
            if(globalDataMap.containsKey(rootProject)) return
            globalDataMap[rootProject] = GlobalData().apply {
                this.rootProject = rootProject
            }
            threadProjectMap[Thread.currentThread()] = rootProject
        }

        fun refresh() {
            globalDataMap = ConcurrentHashMap<Project, GlobalData>()
            threadProjectMap = ConcurrentHashMap<Thread, Project>()
        }
    }

    lateinit var rootProject: Project

    val mavenPublish: MavenPublish = MavenPublish()
}

private var globalDataMap = ConcurrentHashMap<Project, GlobalData>()

private var threadProjectMap = ConcurrentHashMap<Thread, Project>()

/*
 * 仅供配置阶段使用，因为在配置阶段，一个线程对应一个构建项，也对应这个构建项的rootProject，可以通过当前线程
 * 获取到对应的构建项的rootProject，然后可以进一步获取到构建项对应的globalData。
 * 但执行阶段时，一个构建项中的多个任务可能同时被多个不同的线程执行，因此不能用这种方式获取。
 */
internal val globalData: GlobalData
    get() = globalDataMap[threadProjectMap[Thread.currentThread()]]!!

internal val Project.globalDataOfRoot: GlobalData
    get() = globalDataMap[rootProject]!!

val currentProject: Project
    get() = globalData.rootProject.currentProject
