/*
 * Project Name: My Electricity
 * Created by Mohamad Ikhwan Rosdi
 * Copyright © 2024. All rights reserved.
 * Last modified 04/05/2024, 2:15 pm
 *
 */

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

rootProject.name = "My Electricity"
include(":app")
 