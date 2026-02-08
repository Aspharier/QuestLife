package com.aspharier.questlife.core.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aspharier.questlife.navigation.NavRoute

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar(
        tonalElevation = 6.dp
    ) {
        listOf(
            NavRoute.Home,
            NavRoute.Habits,
            NavRoute.Quests,
            NavRoute.World,
            NavRoute.Settings
        ).forEach { route ->
            NavigationBarItem(
                selected = currentRoute == route.route,
                onClick = {
                    navController.navigate(route.route) {
                        popUpTo(NavRoute.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Text(
                        text = route.route.first().uppercase(),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                label = {
                    Text(route.route)
                }
            )
        }
    }

}