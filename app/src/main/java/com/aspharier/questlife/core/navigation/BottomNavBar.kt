package com.aspharier.questlife.core.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.navigation.NavRoute

private data class NavItem(val route: NavRoute, val label: String, val icon: ImageVector)

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String?) {
    val items =
            listOf(
                    NavItem(NavRoute.Home, "Home", Icons.Filled.Home),
                    NavItem(NavRoute.Habits, "Habits", Icons.Filled.CheckCircle),
                    NavItem(NavRoute.Quests, "Quests", Icons.Filled.AutoStories),
                    NavItem(NavRoute.Equipment, "Equip", Icons.Filled.Shield),
                    NavItem(NavRoute.Settings, "Settings", Icons.Filled.Settings),
            )

    val gameColors = LocalGameColors.current

    Column {
        // Subtle top hairline
        Box(
                modifier =
                        Modifier.fillMaxWidth()
                                .height(0.5.dp)
                                .background(gameColors.navBarBorder.copy(alpha = 0.25f))
        )

        Row(
                modifier =
                        Modifier.fillMaxWidth()
                                .background(gameColors.navBarBackground)
                                .navigationBarsPadding()
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route.route
                GameNavItem(
                        item = item,
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route.route) {
                                popUpTo(NavRoute.Home.route)
                                launchSingleTop = true
                            }
                        }
                )
            }
        }
    }
}

@Composable
private fun GameNavItem(item: NavItem, selected: Boolean, onClick: () -> Unit) {
    val gameColors = LocalGameColors.current
    val interactionSource = remember { MutableInteractionSource() }

    val scale by
            animateFloatAsState(
                    targetValue = if (selected) 1.04f else 1f,
                    animationSpec = tween(200),
                    label = "navScale"
            )

    val iconColor = if (selected) gameColors.navBarSelected else gameColors.navBarUnselected
    val labelColor = if (selected) gameColors.navBarSelected else gameColors.navBarUnselected

    Column(
            modifier =
                    Modifier.graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .clip(RoundedCornerShape(12.dp))
                            .then(
                                    if (selected) {
                                        Modifier.drawBehind {
                                            drawRoundRect(
                                                    color =
                                                            gameColors.navBarSelected.copy(
                                                                    alpha = 0.1f
                                                            ),
                                                    cornerRadius = CornerRadius(12.dp.toPx())
                                            )
                                        }
                                    } else Modifier
                            )
                            .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = onClick
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
        )
        Text(
                text = item.label,
                fontSize = 10.sp,
                color = labelColor,
                style = MaterialTheme.typography.labelSmall
        )
    }
}
