package com.aspharier.questlife.presentation.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.clickable
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val darkModeEnabled by viewModel.darkModeEnabled.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val context = LocalContext.current

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 96.dp)) {
        item {
            Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        item { SettingsSectionLabel("Account") }

        item {
            SettingsItem(
                    icon = Icons.Filled.Person,
                    title = "Profile",
                    subtitle = "Edit your hero profile",
                    onClick = { Toast.makeText(context, "Opening Profile...", Toast.LENGTH_SHORT).show() }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp))
        }

        item { SettingsSectionLabel("Appearance") }

        item {
            ListItem(
                    headlineContent = { Text("Dark Mode") },
                    supportingContent = { Text("Toggle dark / light theme") },
                    leadingContent = {
                        Icon(
                                Icons.Filled.DarkMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingContent = {
                        Switch(
                                checked = darkModeEnabled,
                                onCheckedChange = { viewModel.setDarkModeEnabled(it) }
                        )
                    }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp))
        }

        item { SettingsSectionLabel("Notifications") }

        item {
            ListItem(
                    headlineContent = { Text("Daily Reminders") },
                    supportingContent = { Text("Get reminded to complete your habits") },
                    leadingContent = {
                        Icon(
                                Icons.Filled.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingContent = {
                        Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { viewModel.setNotificationsEnabled(it) }
                        )
                    }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp))
        }

        item { SettingsSectionLabel("More") }

        item {
            SettingsItem(
                    icon = Icons.Filled.Star,
                    title = "Rate QuestLife",
                    subtitle = "Leave a review on the Play Store",
                    onClick = { Toast.makeText(context, "Opening Play Store...", Toast.LENGTH_SHORT).show() }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp))
        }

        item {
            SettingsItem(
                    icon = Icons.Filled.Security,
                    title = "Privacy Policy",
                    subtitle = "View our privacy policy",
                    onClick = { Toast.makeText(context, "Opening Privacy Policy...", Toast.LENGTH_SHORT).show() }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 56.dp))
        }

        item {
            SettingsItem(
                icon = Icons.Filled.Info, 
                title = "About", 
                subtitle = "QuestLife v1.0",
                onClick = { Toast.makeText(context, "QuestLife version 1.0", Toast.LENGTH_SHORT).show() }
            )
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun SettingsItem(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    ListItem(
            modifier = Modifier.clickable { onClick() },
            headlineContent = { Text(title) },
            supportingContent = { Text(subtitle) },
            leadingContent = {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
    )
}
