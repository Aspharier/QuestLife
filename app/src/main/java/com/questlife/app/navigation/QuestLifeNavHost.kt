package com.questlife.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.questlife.core.navigation.NavRoute
import com.questlife.presentation.home.HomeScreen
import com.questlife.presentation.habits.HabitsScreen
import com.questlife.presentation.quests.QuestsScreen
import com.questlife.presentation.world.WorldScreen
import com.questlife.presentation.avatar.AvatarScreen
import com.questlife.presentation.skills.SkillsScreen
import com.questlife.presentation.settings.SettingsScreen

@Composable
fun QuestLifeNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route
    ) {
        composable(NavRoute.Home.route) { HomeScreen() }
        composable(NavRoute.Habits.route) { HabitsScreen() }
        composable(NavRoute.Quests.route) { QuestsScreen() }
        composable(NavRoute.World.route) { WorldScreen() }
        composable(NavRoute.Avatar.route) { AvatarScreen() }
        composable(NavRoute.Skills.route) { SkillsScreen() }
        composable(NavRoute.Settings.route) { SettingsScreen() }
    }
}
