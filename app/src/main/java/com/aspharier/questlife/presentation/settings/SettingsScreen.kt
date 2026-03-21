package com.aspharier.questlife.presentation.settings

import android.widget.Toast
import androidx.compose.ui.platform.LocalUriHandler
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.remember
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: androidx.navigation.NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeType by viewModel.themeType.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val notificationTime by viewModel.notificationTime.collectAsState()
    val context = LocalContext.current

    GameScreenBackground {
        androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), 
                contentPadding = PaddingValues(bottom = 180.dp),
                flingBehavior = androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior()
            ) {
                item {
                    GameSectionHeader(
                            title = "Settings",
                            modifier = Modifier.padding(top = 12.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                }


                // Appearance Section
                item { SettingsSectionLabel("Appearance") }
                item {
                    GameSettingsPanel {
                        Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            var expanded by remember { androidx.compose.runtime.mutableStateOf(false) }

                            androidx.compose.foundation.layout.Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { expanded = true }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                val currentThemeName = when(themeType) {
                                    com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK -> "Deep Dark"
                                    com.aspharier.questlife.core.ui.theme.ThemeType.MYSTIC_PURPLE -> "Mystic Purple"
                                    com.aspharier.questlife.core.ui.theme.ThemeType.DARK_GREEN -> "Forest Green"
                                    com.aspharier.questlife.core.ui.theme.ThemeType.CRIMSON_NIGHT -> "Crimson Night"
                                    com.aspharier.questlife.core.ui.theme.ThemeType.OCEAN_DEPTHS -> "Ocean Depths"
                                    com.aspharier.questlife.core.ui.theme.ThemeType.SUNSET_BLAZE -> "Sunset Blaze"
                                    com.aspharier.questlife.core.ui.theme.ThemeType.NEON_CYBER -> "Neon Cyber"
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = currentThemeName,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Icon(
                                        imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                androidx.compose.material3.DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    com.aspharier.questlife.core.ui.theme.ThemeType.values().forEach { type ->
                                        val typeName = when(type) {
                                            com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK -> "Deep Dark"
                                            com.aspharier.questlife.core.ui.theme.ThemeType.MYSTIC_PURPLE -> "Mystic Purple"
                                            com.aspharier.questlife.core.ui.theme.ThemeType.DARK_GREEN -> "Forest Green"
                                            com.aspharier.questlife.core.ui.theme.ThemeType.CRIMSON_NIGHT -> "Crimson Night"
                                            com.aspharier.questlife.core.ui.theme.ThemeType.OCEAN_DEPTHS -> "Ocean Depths"
                                            com.aspharier.questlife.core.ui.theme.ThemeType.SUNSET_BLAZE -> "Sunset Blaze"
                                            com.aspharier.questlife.core.ui.theme.ThemeType.NEON_CYBER -> "Neon Cyber"
                                        }
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = { 
                                                Text(
                                                    text = typeName,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Center
                                                ) 
                                            },
                                            onClick = {
                                                viewModel.setThemeType(type)
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Notifications Section
                item { SettingsSectionLabel("Notifications") }
                item {
                    GameSettingsPanel {
                        GameSettingsToggle(
                                icon = Icons.Filled.Notifications,
                                title = "Daily Reminders",
                                subtitle = "Get reminded to complete your habits",
                                checked = notificationsEnabled,
                                onCheckedChange = { viewModel.setNotificationsEnabled(it) }
                        )
                        if (notificationsEnabled) {
                            val timeParts = notificationTime.split(":")
                            val selectedHour = timeParts.getOrNull(0)?.toIntOrNull() ?: 9
                            val selectedMinute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
                            val timePickerDialog = remember {
                                android.app.TimePickerDialog(
                                    context,
                                    { _, hourOfDay, minute ->
                                        viewModel.setNotificationTime(String.format("%02d:%02d", hourOfDay, minute))
                                    },
                                    selectedHour,
                                    selectedMinute,
                                    false
                                )
                            }

                            Spacer(Modifier.height(2.dp))
                            GameSettingsItem(
                                    icon = Icons.Filled.Schedule,
                                    title = "Reminder Time",
                                    subtitle = notificationTime,
                                    onClick = {
                                        timePickerDialog.updateTime(selectedHour, selectedMinute)
                                        timePickerDialog.show()
                                    }
                            )
                        }
                    }
                }

                // More Section
                item { SettingsSectionLabel("More") }
                item {
                    GameSettingsPanel {
                        Column {
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
            
            // GitHub Sticky Footer
            val uriHandler = LocalUriHandler.current
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 96.dp), // Positioned right above the bottom navigation bar
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                )
                Spacer(Modifier.height(16.dp))
                
                Text(
                    text = "Made with ❤️",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
                Icon(
                    painter = androidx.compose.ui.res.painterResource(id = com.questlife.app.R.drawable.ic_github),
                    contentDescription = "GitHub",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .clickable { uriHandler.openUri("https://github.com/Aspharier") },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Aspharier",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
