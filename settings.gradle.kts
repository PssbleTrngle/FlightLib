pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
        maven { url = uri("https://maven.architectury.dev/") }
        maven { url = uri("https://maven.neoforged.net/releases/") }
    }
}

include(
    "api", "common",
    "forge-api", "forge",
    "fabric",
)

