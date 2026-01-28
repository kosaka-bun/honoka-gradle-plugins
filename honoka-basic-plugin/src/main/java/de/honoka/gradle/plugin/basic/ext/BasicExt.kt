package de.honoka.gradle.plugin.basic.ext

import de.honoka.gradle.util.dsl.BaseExtension

open class BasicExt : BaseExtension() {

    override fun defineDsls() {
        defineDsl("dependencies", DependenciesExt::class)
        defineDsl("publishing", PublishingExt::class)
        defineDsl("configs", ConfigsExt::class)
    }
}
