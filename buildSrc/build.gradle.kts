plugins {
    `kotlin-dsl`
}

sourceSets.create("stub", Action {
    java.srcDir("/src/stub/java")
})

configurations {
    named("stubImplementation") {
        extendsFrom(configurations.implementation.get())
    }
}

dependencies {
    compileOnly(sourceSets["stub"].output)
}
