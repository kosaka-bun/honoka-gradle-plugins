plugins {
    `kotlin-dsl`
}

sourceSets {
    fun srcDirOf(projectName: String): String = "../$projectName/src/main/java"

    main {
        java {
            srcDir(srcDirOf("honoka-gradle-utils"))
            srcDir(srcDirOf("honoka-basic-plugin"))
        }
    }

    create("stub") {
        java.srcDir(srcDirOf("stubs"))
    }
}

configurations {
    named("stubImplementation") {
        extendsFrom(implementation.get())
    }
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlin.allopen)
    compileOnly(sourceSets["stub"].output)
}
