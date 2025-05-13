pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Plan"
include(":app")
include(":base")
include(":domain")
include(":features")
include(":features:chat")
include(":features:createPost")
include(":features:PostList")
include(":features:profile")
include(":features:login")
include(":features:signUp")
include(":features:validateEmail")
