package de.honoka.gradle.plugin.spring.ext

import de.honoka.gradle.util.dsl.BaseExtension

open class SpringExt : BaseExtension() {

    override fun defineDsls() {
        defineDsl("bootJar", BootJarExt::class)
    }
}
