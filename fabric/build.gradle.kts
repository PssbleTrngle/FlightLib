val minecraft_version: String by extra
val mod_version: String by extra
val mod_id: String by extra
val fabric_loader_version: String by extra
val fabric_version: String by extra
val kotlin_fabric_version: String by extra
val trinkets_version: String by extra

plugins {
    id("fabric-loom") version ("1.0-SNAPSHOT")
    id("idea")
    id("org.jetbrains.kotlin.jvm")
}

base {
    archivesName.set("${mod_id}-fabric-${mod_version}")
}

val dependencyProjects = listOf(
    project(":api"),
    project(":common"),
)

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")

    implementation("com.google.code.findbugs:jsr305:3.0.2")

    modImplementation("net.fabricmc:fabric-language-kotlin:${kotlin_fabric_version}")

    modImplementation("dev.emi:trinkets:${trinkets_version}")

    dependencyProjects.forEach { implementation(it) }
}

loom {
    mixin {
        defaultRefmapName.set("${mod_id}.refmap.json")
    }

    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run/server")
        }
    }
}

tasks.withType<JavaCompile> {
    source(project(":common").sourceSets["main"].allSource)
}

tasks.jar {
    from(sourceSets.main.get().output)
    dependencyProjects.forEach {
        from(it.sourceSets.main.get().output)
    }
}