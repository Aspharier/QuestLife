package com.aspharier.questlife.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aspharier.questlife.navigation.NavRoute

private data class NavItem(val route: NavRoute, val label: String, val icon: ImageVector)

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String?) {
    val items =
            listOf(
                    NavItem(NavRoute.Home, "Home", Icons.Filled.Home),
                    NavItem(NavRoute.Habits, "Habits", Icons.Filled.CheckCircle),
                    NavItem(NavRoute.Quests, "Quests", Icons.Filled.Star),
                    NavItem(NavRoute.World, "World", Icons.Filled.Public),
                    NavItem(NavRoute.Settings, "Settings", Icons.Filled.Settings),
            )

    NavigationBar(tonalElevation = 6.dp) {
        items.forEach { item ->
            NavigationBarItem(
                    selected = currentRoute == item.route.route,
                    onClick = {
                        navController.navigate(item.route.route) {
                            popUpTo(NavRoute.Home.route)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                    label = { Text(item.label) }
            )
        }
    }
}
