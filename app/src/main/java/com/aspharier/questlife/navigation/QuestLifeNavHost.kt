package com.aspharier.questlife.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aspharier.questlife.presentation.home.HomeScreen
import com.aspharier.questlife.presentation.habits.HabitsScreen
import com.aspharier.questlife.presentation.quests.QuestsScreen
import com.aspharier.questlife.presentation.world.WorldScreen
import com.aspharier.questlife.presentation.avatar.AvatarScreen
import com.aspharier.questlife.presentation.skills.SkillsScreen
import com.aspharier.questlife.presentation.settings.SettingsScreen

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
