val curios_forge_version: String by extra

plugins {
    id("com.possible-triangle.forge")
}

forge {
    enableMixins()

    dependOn(project(":api"))
    dependOn(project(":forge-api"))
    dependOn(project(":common"))
}

dependencies {
    modImplementation("top.theillusivec4.curios:curios-forge:${curios_forge_version}")
}