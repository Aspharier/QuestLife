package com.aspharier.questlife.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aspharier.questlife.navigation.NavRoute
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.presentation.screens.GameScreenBackground

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeType by viewModel.themeType.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val context = LocalContext.current

    GameScreenBackground {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 96.dp), flingBehavior = androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior()) {
        item {
            GameSectionHeader(
                    title = "Settings",
                    modifier = Modifier.padding(top = 12.dp)
            )
            Spacer(Modifier.height(8.dp))
        }

        // Account Section
        item { SettingsSectionLabel("⚔️ Account") }
        item {
            GameSettingsPanel {
                GameSettingsItem(
                        icon = Icons.Filled.Person,
                        title = "Profile",
                        subtitle = "Edit your hero profile",
                        onClick = {
                            Toast.makeText(context, "Opening Profile...", Toast.LENGTH_SHORT).show()
                        }
                )
                Spacer(Modifier.height(2.dp))
                GameSettingsItem(
                        icon = Icons.Filled.Person, // Could use a more specific icon if available
                        title = "Customize Avatar",
                        subtitle = "Equip your gear and change appearance",
                        onClick = {
                            navController.navigate(NavRoute.Equipment.route)
                        }
                )
            }
        }

        // Appearance Section
        item { SettingsSectionLabel("🎨 Appearance") }
        item {
            GameSettingsPanel {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                ) {
                    androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
                        GameSettingsThemeButton(
                            title = "Deep Dark",
                            icon = Icons.Filled.DarkMode,
                            selected = themeType == com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK,
                            onClick = { viewModel.setThemeType(com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK) }
                        )
                    }
                    androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
                        GameSettingsThemeButton(
                            title = "Purple",
                            icon = Icons.Filled.Star,
                            selected = themeType == com.aspharier.questlife.core.ui.theme.ThemeType.MYSTIC_PURPLE,
                            onClick = { viewModel.setThemeType(com.aspharier.questlife.core.ui.theme.ThemeType.MYSTIC_PURPLE) }
                        )
                    }
                    androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
                        GameSettingsThemeButton(
                            title = "Green",
                            icon = Icons.Filled.CheckCircle,
                            selected = themeType == com.aspharier.questlife.core.ui.theme.ThemeType.DARK_GREEN,
                            onClick = { viewModel.setThemeType(com.aspharier.questlife.core.ui.theme.ThemeType.DARK_GREEN) }
                        )
                    }
                }
            }
        }

        // Notifications Section
        item { SettingsSectionLabel("🔔 Notifications") }
        item {
            GameSettingsPanel {
                GameSettingsToggle(
                        icon = Icons.Filled.Notifications,
                        title = "Daily Reminders",
                        subtitle = "Get reminded to complete your habits",
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.setNotificationsEnabled(it) }
                )
            }
        }

        // More Section
        item { SettingsSectionLabel("📋 More") }
        item {
            GameSettingsPanel {
                Column {
                    GameSettingsItem(
                            icon = Icons.Filled.Star,
                            title = "Rate QuestLife",
                            subtitle = "Leave a review on the Play Store",
                            onClick = {
                                Toast.makeText(context, "Opening Play Store...", Toast.LENGTH_SHORT)
                                        .show()
                            }
                    )
                    Spacer(Modifier.height(2.dp))
                    GameSettingsItem(
                            icon = Icons.Filled.Security,
                            title = "Privacy Policy",
                            subtitle = "View our privacy policy",
                            onClick = {
                                Toast.makeText(
                                                context,
                                                "Opening Privacy Policy...",
                                                Toast.LENGTH_SHORT
                                        )
                                        .show()
                            }
                    )
                    Spacer(Modifier.height(2.dp))
                    GameSettingsItem(
                            icon = Icons.Filled.Info,
                            title = "About",
                            subtitle = "QuestLife v1.0",
                            onClick = {
                                Toast.makeText(context, "QuestLife version 1.0", Toast.LENGTH_SHORT)
                                        .show()
                            }
                    )
                }
            }
        }
        }
    }
}

@Composable
private fun SettingsSectionLabel(label: String) {
    Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)
    )
}

@Composable
private fun GameSettingsPanel(content: @Composable () -> Unit) {
    GamePanel(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)) {
        Column(modifier = Modifier.padding(4.dp)) { content() }
    }
}

@Composable
private fun GameSettingsItem(
        icon: ImageVector,
        title: String,
        subtitle: String,
        onClick: () -> Unit
) {
    Row(
            modifier =
                    Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(onClick = onClick)
                            .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(14.dp))
        Column {
            Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
            )
            Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun GameSettingsToggle(
        icon: ImageVector,
        title: String,
        subtitle: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit
) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
            )
            Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors =
                        SwitchDefaults.colors(
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary
                        )
        )
    }
}

@Composable
private fun GameSettingsThemeButton(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val gameColors = com.aspharier.questlife.core.ui.theme.LocalGameColors.current
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                2.dp,
                if (selected) MaterialTheme.colorScheme.primary else gameColors.panelBorder,
                RoundedCornerShape(12.dp)
            )
            .background(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}
