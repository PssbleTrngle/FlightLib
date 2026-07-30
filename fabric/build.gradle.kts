plugins {
    id("com.possible-triangle.fabric")
}

fabric {
    dependOn(project(":api"))
    dependOn(project(":common"))

    accessWidener()
}

dependencies {
    modImplementation(libs.trinkets)
    modImplementation(libs.cardinal.components.base)
}

tasks.test {
    enabled = false
}
