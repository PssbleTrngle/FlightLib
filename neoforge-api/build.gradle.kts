plugins {
    id("com.possible-triangle.neoforge")
}

neoforge {
    dependOn(project(":api"))
}
