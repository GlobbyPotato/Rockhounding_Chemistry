pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "net.minecraftforge.gradle") {
                useModule("${requested.id}:ForgeGradle:${requested.version}")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        maven {
            name = "MinecraftForge"
            url = uri("https://maven.minecraftforge.net/")
        }
        maven {
            name = "FancyGradle"
            url = uri("https://gitlab.com/api/v4/projects/26758973/packages/maven")
        }
    }
}

rootProject.name = "Rockhounding_Chemistry"


if(file("rockhounding_core").exists()) {
    includeBuild("rockhounding_core") {
        dependencySubstitution {
            substitute(module("curse.maven:rockhounding_core-271298:3287602")).using(project(":"))
        }
    }
}
