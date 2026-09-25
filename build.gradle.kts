plugins {
    java
    application
    idea
    id ("com.diffplug.spotless") version "8.2.1"
    id("com.gradleup.shadow") version "9.3.1"
}

apply(from = "repositories.gradle")
apply(from = "dependencies.gradle")

group = property("group") as String
version = property("version") as String

application {
    mainClass.set("Run")
    applicationDefaultJvmArgs = listOf(
        "--enable-native-access=ALL-UNNAMED"
    )
}

tasks {
    jar {
        manifest {
            attributes(
                "Main-Class" to application.mainClass.get()
            )
        }
    }

    shadowJar {
        archiveClassifier.set("all")
        manifest {
            attributes(
                "Main-Class" to application.mainClass.get()
            )
        }
    }
}

spotless {
    java {
        target("src/**/*.java")
    }

    format("misc") {
        target(
            "*.gradle.kts",
            "*.md",
            ".gitignore"
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
}
