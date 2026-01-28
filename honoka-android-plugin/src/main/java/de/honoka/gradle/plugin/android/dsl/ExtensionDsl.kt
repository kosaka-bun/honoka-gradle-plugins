package de.honoka.gradle.plugin.android.dsl

import de.honoka.gradle.plugin.android.ext.AndroidExt
import de.honoka.gradle.plugin.basic.HonokaExt
import de.honoka.gradle.util.dsl.asExt
import de.honoka.gradle.util.dsl.find

val HonokaExt.android: AndroidExt
    get() = asExt().extensions.find("android")
