package de.honoka.gradle.util.data

import de.honoka.gradle.util.dsl.currentProject
import org.gradle.api.Project
import java.util.concurrent.ConcurrentHashMap

class GlobalDataManager<T : AbstractGlobalData>(private val clazz: Class<T>) {

    private var globalDataMap = ConcurrentHashMap<Project, T>()

    private var threadProjectMap = ConcurrentHashMap<Thread, Project>()

    /*
     * 仅供配置阶段使用，因为在配置阶段，一个线程对应一个构建项，也对应这个构建项的rootProject，可以通过当前线程
     * 获取到对应的构建项的rootProject，然后可以进一步获取到构建项对应的globalData。
     * 但执行阶段时，一个构建项中的多个任务可能同时被多个不同的线程执行，因此不能用这种方式获取。
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val globalData: T
        get() = globalDataMap[threadProjectMap[Thread.currentThread()]]!!

    val currentProject: Project
        get() = globalData.rootProject.currentProject

    fun init(project: Project) {
        val rootProject = project.rootProject
        if(globalDataMap.containsKey(rootProject)) return
        globalDataMap[rootProject] = clazz.getConstructor().newInstance().apply {
            this.rootProject = rootProject
        }
        threadProjectMap[Thread.currentThread()] = rootProject
    }

    fun refresh() {
        globalDataMap = ConcurrentHashMap<Project, T>()
        threadProjectMap = ConcurrentHashMap<Thread, Project>()
    }

    fun getGlobalData(project: Project): T = globalDataMap[project.rootProject]!!
}

abstract class AbstractGlobalData {

    lateinit var rootProject: Project
}

abstract class AbstractGlobalDataDefinitions<T : AbstractGlobalData>(
    private val globalDataManager: GlobalDataManager<T>
) {

    val globalData: T
        get() = globalDataManager.globalData

    val Project.globalDataOfRoot: T
        get() = globalDataManager.getGlobalData(this)

    val currentProject: Project
        get() = globalDataManager.currentProject
}
