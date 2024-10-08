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

rootProject.name = "pokit"
include(":app")
include(":domain")
include(":data")
include(":feature:login")
include(":core:ui")
include(":feature:addlink")
include(":feature:addpokit")
include(":feature:login")
include(":feature:pokitdetail")
include(":feature:search")
include(":feature:settings")
include(":feature:alarm")
include(":feature:home")
include(":core:feature")
