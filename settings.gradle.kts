pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}

plugins {
    id("com.possible-triangle.helper") version ("1.4")
}

include(
    "api",
    "common",
    "neoforge-api",
    "neoforge",
    "fabric",
)
