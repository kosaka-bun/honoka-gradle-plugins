version = libs.versions.honoka.gradle.dsl.get()

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group as String
            artifactId = project.name
            this.version = version
            from(components["java"])
        }
    }
}
