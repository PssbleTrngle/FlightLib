val minecraft_version: String by extra
val mod_version: String by extra
val mod_id: String by extra

plugins {
    id("org.spongepowered.gradle.vanilla") version ("0.2.1-SNAPSHOT")
    id("org.jetbrains.kotlin.jvm")
}

base {
    archivesName.set("${mod_id}-common-${mod_version}")
}

minecraft {
    version(minecraft_version)
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
    implementation("com.google.code.findbugs:jsr305:3.0.2")

    implementation(project(":api"))
}