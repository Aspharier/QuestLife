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

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.content.ContextCompat
@Composable
fun SettingsScreen(
    navController: androidx.navigation.NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeType by viewModel.themeType.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val notificationTime by viewModel.notificationTime.collectAsState()
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.setNotificationsEnabled(true)
        } else {
            Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
            viewModel.setNotificationsEnabled(false)
        }
    }

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
                        Column(
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            val themes = com.aspharier.questlife.core.ui.theme.ThemeType.values().toList()
                            val row1 = themes.take(4)
                            val row2 = themes.drop(4)
                            
                            Row(
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                row1.forEach { type ->
                                    ThemeIcon(type, type == themeType) { viewModel.setThemeType(type) }
                                }
                            }
                            Row(
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                row2.forEach { type ->
                                    ThemeIcon(type, type == themeType) { viewModel.setThemeType(type) }
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
                                onCheckedChange = { isChecked ->
                                    if (isChecked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                                            viewModel.setNotificationsEnabled(true)
                                        } else {
                                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        }
                                    } else {
                                        viewModel.setNotificationsEnabled(isChecked)
                                    }
                                }
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
private fun ThemeIcon(
    type: com.aspharier.questlife.core.ui.theme.ThemeType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val typeName = when(type) {
        com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK -> "Deep"
        com.aspharier.questlife.core.ui.theme.ThemeType.MYSTIC_PURPLE -> "Mystic"
        com.aspharier.questlife.core.ui.theme.ThemeType.DARK_GREEN -> "Forest"
        com.aspharier.questlife.core.ui.theme.ThemeType.CRIMSON_NIGHT -> "Crimson"
        com.aspharier.questlife.core.ui.theme.ThemeType.OCEAN_DEPTHS -> "Ocean"
        com.aspharier.questlife.core.ui.theme.ThemeType.SUNSET_BLAZE -> "Sunset"
        com.aspharier.questlife.core.ui.theme.ThemeType.NEON_CYBER -> "Neon"
    }
    val itemColor = when(type) {
        com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK -> androidx.compose.ui.graphics.Color(0xFF8B5CF6)
        com.aspharier.questlife.core.ui.theme.ThemeType.MYSTIC_PURPLE -> androidx.compose.ui.graphics.Color(0xFFD946EF)
        com.aspharier.questlife.core.ui.theme.ThemeType.DARK_GREEN -> androidx.compose.ui.graphics.Color(0xFF10B981)
        com.aspharier.questlife.core.ui.theme.ThemeType.CRIMSON_NIGHT -> androidx.compose.ui.graphics.Color(0xFFEF4444)
        com.aspharier.questlife.core.ui.theme.ThemeType.OCEAN_DEPTHS -> androidx.compose.ui.graphics.Color(0xFF06B6D4)
        com.aspharier.questlife.core.ui.theme.ThemeType.SUNSET_BLAZE -> androidx.compose.ui.graphics.Color(0xFFF97316)
        com.aspharier.questlife.core.ui.theme.ThemeType.NEON_CYBER -> androidx.compose.ui.graphics.Color(0xFF22D3EE)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(44.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(itemColor)
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = typeName,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
