plugins {
    id("com.possible-triangle.core")
    id("com.possible-triangle.vanilla") apply false
    id("com.possible-triangle.forge") apply false
    id("com.possible-triangle.fabric") apply false
}

withKotlin()

subprojects {
    apply(plugin = "com.possible-triangle.core")

    repositories {
        maven {
            url = uri("https://maven.theillusivec4.top/")
            content {
                includeGroup("top.theillusivec4.curios")
            }
        }

        maven {
            url = uri("https://maven.terraformersmc.com/")
            content {
                includeGroup("dev.emi")
            }
        }

        maven {
            url = uri("https://maven.ladysnake.org/releases")
            content {
                includeGroup("dev.onyxstudios.cardinal-components-api")
            }
        }
    }

    upload {
        maven {
            nexus()
        }
    }
}

enableSonarQube()
enableSpotless()
