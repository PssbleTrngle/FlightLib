val curios_forge_version: String by extra
val mc_version: String by extra

plugins {
    id("com.possible-triangle.neoforge")
}

neoforge {
    dependOn(project(":api"))
    dependOn(project(":neoforge-api"))
    dependOn(project(":common"))
}

dependencies {
    modImplementation("top.theillusivec4.curios:curios-neoforge:${curios_forge_version}+${mc_version}")
}