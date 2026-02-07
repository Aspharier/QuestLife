package com.questlife.core.navigation

sealed class NavRoute(val route: String) {
    object Home : NavRoute("home")
    object Habits : NavRoute("habits")
    object Quests : NavRoute("quests")
    object World : NavRoute("world")
    object Avatar : NavRoute("avatar")
    object Skills : NavRoute("skills")
    object Settings : NavRoute("settings")
}
