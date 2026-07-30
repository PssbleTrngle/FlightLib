plugins {
    id("com.possible-triangle.neoforge")
}

neoforge {
    dependOn(project(":api"))
    dependOn(project(":neoforge-api"))
    dependOn(project(":common"))

    injectInterfaces()
}

dependencies {
    modImplementation(libs.curios)
}
