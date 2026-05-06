val trinkets_version: String by extra
val cca_version: String by extra

plugins {
    id("com.possible-triangle.fabric")
}

fabric {
    dependOn(project(":api"))
    dependOn(project(":common"))

    accessWidener()
}

dependencies {
    modImplementation("dev.emi:trinkets:${trinkets_version}")
    modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-base:${cca_version}")
}

tasks.test {
    enabled = false
}
