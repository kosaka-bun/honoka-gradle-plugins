package de.honoka.gradle.buildsrc.util.listener

import org.gradle.api.invocation.Gradle
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.build.event.BuildEventsListenerRegistry
import org.gradle.tooling.events.FinishEvent
import org.gradle.tooling.events.OperationCompletionListener

private var registered: Boolean = false

abstract class BuildFinishedListener : BuildService<BuildServiceParameters.None>,
    OperationCompletionListener, AutoCloseable {

    var onClose: () -> Unit = {}

    override fun onFinish(event: FinishEvent) {}

    override fun close() {
        onClose()
        registered = false
    }
}

@Synchronized
fun BuildEventsListenerRegistry.onBuildFinished(gradle: Gradle, namePrefix: String, block: () -> Unit) {
    if(registered) return
    val provider = gradle.sharedServices.registerIfAbsent(
        "$namePrefix${BuildFinishedListener::class.simpleName}",
        BuildFinishedListener::class.java
    ) {}
    provider.get().onClose = block
    onTaskCompletion(provider)
    registered = true
}
