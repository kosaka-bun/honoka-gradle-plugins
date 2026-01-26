plugins {
    `kotlin-dsl`
}

sourceSets {
    val suffix = "src/main/java"

    main {
        java.srcDirs(
            "../honoka-gradle-utils/$suffix",
            "../honoka-basic-plugin/$suffix"
        )
    }

    create("stub") {
        java.srcDir("../stubs/$suffix")
    }
}

configurations {
    named("stubImplementation") {
        extendsFrom(implementation.get())
    }
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    compileOnly(sourceSets["stub"].output)
}
