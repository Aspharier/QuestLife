package com.aspharier.questlife.core.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.tween
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
import com.aspharier.questlife.presentation.equipment.EquipmentScreen
import com.aspharier.questlife.presentation.habits.HabitsScreen
import com.aspharier.questlife.presentation.home.HomeScreen
import com.aspharier.questlife.presentation.quests.QuestsScreen
import com.aspharier.questlife.presentation.settings.SettingsScreen
import com.aspharier.questlife.presentation.world.WorldScreen

@Composable
fun QuestLifeNavHost() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
            bottomBar = { BottomNavBar(navController = navController, currentRoute = currentRoute) }
    ) { paddingValues ->
        NavHost(
                navController = navController,
                startDestination = NavRoute.Home.route,
                modifier = Modifier.padding(paddingValues),
                enterTransition = {
                    fadeIn(animationSpec = tween(400)) +
                            slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                    animationSpec = tween(400, easing = EaseInOutQuart)
                            )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(400)) +
                            slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                    animationSpec = tween(400, easing = EaseInOutQuart)
                            )
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(400)) +
                            slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                                    animationSpec = tween(400, easing = EaseInOutQuart)
                            )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(400)) +
                            slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                                    animationSpec = tween(400, easing = EaseInOutQuart)
                            )
                }
        ) {
            // routes....
            composable(NavRoute.Home.route) { HomeScreen(navController) }
            composable(NavRoute.Habits.route) { HabitsScreen() }
            composable(NavRoute.Quests.route) { QuestsScreen() }
            composable(NavRoute.World.route) { WorldScreen() }
            composable(NavRoute.Settings.route) { SettingsScreen(navController = navController) }
            composable(NavRoute.Equipment.route) { EquipmentScreen() }
        }
    }
}
