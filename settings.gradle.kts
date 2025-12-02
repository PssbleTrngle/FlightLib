pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.2")
}

include(
    "api",
    "common",
    "neoforge-api",
    "neoforge",
    "fabric",
)
