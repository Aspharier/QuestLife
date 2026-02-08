package com.aspharier.questlife.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aspharier.questlife.navigation.NavRoute
import com.aspharier.questlife.presentation.habits.HabitsScreen
import com.aspharier.questlife.presentation.home.HomeScreen
import com.aspharier.questlife.presentation.quests.QuestsScreen
import com.aspharier.questlife.presentation.settings.SettingsScreen
import com.aspharier.questlife.presentation.world.WorldScreen

@Composable
fun QuestLifeNavHost() {
    val navController = rememberNavController()
    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // routes....
            composable(NavRoute.Home.route) {
                HomeScreen()
            }
            composable(NavRoute.Habits.route) {
                HabitsScreen()
            }
            composable(NavRoute.Quests.route) {
                QuestsScreen()
            }
            composable(NavRoute.World.route) {
                WorldScreen()
            }
            composable(NavRoute.Settings.route) {
                SettingsScreen()
            }
        }
    }
}