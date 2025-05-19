plugins {
    id("com.possible-triangle.gradle") version "0.2.8"
}

withKotlin()

subprojects {
    repositories {
        mavenCentral()

        maven {
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
            content {
                includeGroup("org.spongepowered")
            }
        }

        maven {
            url = uri("https://maven.theillusivec4.top/")
            content {
                includeGroup("top.theillusivec4.curios")
            }
        }

        maven {
            url = uri("https://thedarkcolour.github.io/KotlinForForge/")
            content {
                includeGroup("thedarkcolour")
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
                includeGroup("org.ladysnake.cardinal-components-api")
            }
        }
    }

    enablePublishing {
        githubPackages()
        repositories {
            mavenLocal()
        }
    }
}

enableSonarQube()
enableSpotless()
