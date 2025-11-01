val mod_id: String by extra
val mod_version: String by extra

plugins {
    id("com.possible-triangle.common")
}

base {
    archivesName.set("${mod_id}-api-${mod_version}")
}