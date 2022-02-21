import org.ajoberstar.grgit.Grgit
import java.time.format.DateTimeFormatter
import java.time.Instant

plugins {
    idea
    java
    id("net.minecraftforge.gradle") version "5.1.+"
    id("wtf.gofancy.fancygradle") version "1.1.+"
    id("org.ajoberstar.grgit") version "4.1.1"
}

val versionMC: String by project
val versionForge: String by project
val versionMod: String by project

val versionCore: String by project
val versionRF: String by project
val versionJEI: String by project
val versionTOP: String by project
val versionCT: String by project

group = "com.globbypotato.rockhounding_chemistry"
version = "$versionMC-$versionMod"
setProperty("archivesBaseName", "rockhounding_chemistry")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

minecraft {
    mappings("snapshot", "20171003-1.12")

    runs {
        create("client") {
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            workingDirectory(file("run"))

            mods {
                create("rockhounding_chemistry") {
                    source(sourceSets.getByName("main"))
                }
            }
        }
        create("server") {
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            workingDirectory(file("run-server"))

            mods {
                create("rockhounding_chemistry") {
                    source(sourceSets.getByName("main"))
                }
            }
        }
    }
}

fancyGradle {
    patches {
        resources
        coremods
        codeChickenLib
        asm
    }
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}

repositories {
    mavenCentral()
    maven { // JEI
        name = "Progwml6 maven"
        url = uri("https://dvs1.progwml6.com/files/maven/")
    }
    maven { // JEI Fallback
        name = "ModMaven"
        url = uri("https://modmaven.dev")
    }
    maven { // TOP
        name = "tterrag maven"
        url = uri("https://maven.tterrag.com/")
    }
    maven { // https://www.cursemaven.com/forge
        name = "CurseMaven"
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "$versionMC-$versionForge")

    compileOnly(fg.deobf("mezz.jei:jei_$versionMC:$versionJEI:api"))
    runtimeOnly("mezz.jei:jei_$versionMC:$versionJEI")

    implementation("cofh:RedstoneFlux:$versionRF:deobf")
    implementation("mcjty.theoneprobe:TheOneProbe-1.12:1.12-$versionTOP")
    implementation("curse.maven:hwyla-253449:2568753")

    compileOnly("CraftTweaker2:CraftTweaker2-API:$versionCT")
    implementation("CraftTweaker2:CraftTweaker2-MC1120-Main:1.12-$versionCT")
    compileOnly("CraftTweaker2:ZenScript:$versionCT")

    implementation(
        if(file("rockhounding_core").exists())
            "curse.maven:rockhounding_core-271298:3287602"
        else
            fg.deobf("curse.maven:rockhounding_core-271298:3287602")
    )

    implementation(fg.deobf("curse.maven:guidebook-253874:2989594"))
    implementation(fg.deobf("curse.maven:bookshelf-228525:2836960"))
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Specification-Title" to "RockhoundingChemistry",
            "Specification-Vendor" to "GlobbyPotato",
            "Specification-Version" to "${versionMC}-${versionMod}",
            "Implementation-Title" to project.name,
            "Implementation-Version" to archiveVersion,
            "Implementation-Vendor" to "GlobbyPotato",
            "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        )
    }
}

tasks.register("cloneRHCore") {
    group = "build setup"
    doLast {
        val coreRepo: String by project
        val coreBranch: String? by project

        val repo = Grgit.clone {
            dir = "$projectDir/rockhounding_core"
            uri = coreRepo
            if(coreBranch != null)
                refToCheckout = coreBranch
        }
        println("Cloned libVulpes repository from $coreRepo (current branch: ${repo.branch.current().name})")
    }
}

tasks.processResources {
    inputs.properties(
        "chemistryModVersion" to project.version,
        "mcVersion" to versionMC,
        "coreModVersion" to versionCore
    )

    filesMatching("mcmod.info") {
        expand(
            "chemistryModVersion" to project.version,
            "mcVersion" to versionMC,
            "coreModVersion" to versionCore
        )
    }
}

idea {
    module {
        inheritOutputDirs = true
    }
}