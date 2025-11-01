val mod_version: String by extra
val mod_id: String by extra

plugins {
    id("com.possible-triangle.neoforge")
}

neoforge {
    dependOn(project(":api"))
}

base {
    archivesName.set("${mod_id}-neoforge-api-${mod_version}")
}