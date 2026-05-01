package com.aspharier.questlife.presentation.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aspharier.questlife.core.ui.theme.ThemeType
import com.aspharier.questlife.presentation.screens.GameScreenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Suppress("UNUSED_PARAMETER")
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeType by viewModel.themeType.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val notificationTime by viewModel.notificationTime.collectAsState()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // ── Large Title ────────────────────────────────────────
            item {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 24.dp, top = 20.dp, bottom = 24.dp)
                )
            }

            // ── Appearance Section ─────────────────────────────────
            item { SectionLabel("APPEARANCE") }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    // Dark themes sub-label
                    Text(
                        text = "Dark Realms",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ThemeType.values().filter { !it.isLight }.forEach { type ->
                            ThemeSwatch(
                                type = type,
                                isSelected = type == themeType,
                                onClick = { viewModel.setThemeType(type) }
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // Light themes sub-label
                    Text(
                        text = "Light Realms",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ThemeType.values().filter { it.isLight }.forEach { type ->
                            ThemeSwatch(
                                type = type,
                                isSelected = type == themeType,
                                onClick = { viewModel.setThemeType(type) }
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(8.dp)) }

            // ── Notifications Section ──────────────────────────────
            item { SectionLabel("NOTIFICATIONS") }

            item {
                SettingsToggleRow(
                    icon = Icons.Filled.Notifications,
                    title = "Daily Reminders",
                    subtitle = "Get reminded to complete your habits",
                    checked = notificationsEnabled,
                    onCheckedChange = { isChecked ->
                        if (isChecked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                viewModel.setNotificationsEnabled(true)
                            } else {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        } else {
                            viewModel.setNotificationsEnabled(isChecked)
                        }
                    }
                )
            }

            if (notificationsEnabled) {
                item {
                    val timeParts = notificationTime.split(":")
                    val selectedHour = timeParts.getOrNull(0)?.toIntOrNull() ?: 9
                    val selectedMinute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
                    val timePickerDialog = remember {
                        android.app.TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                viewModel.setNotificationTime(
                                    String.format("%02d:%02d", hourOfDay, minute)
                                )
                            },
                            selectedHour,
                            selectedMinute,
                            false
                        )
                    }

                    SettingsRow(
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

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                )
            }

            // ── More Section ───────────────────────────────────────
            item { SectionLabel("MORE") }

            item {
                SettingsRow(
                    icon = Icons.Filled.Info,
                    title = "About",
                    subtitle = "QuestLife v1.0",
                    onClick = {
                        Toast.makeText(context, "QuestLife version 1.0", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // ── GitHub Footer (inline, never overlaps) ─────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 48.dp, vertical = 0.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.10f)
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Made with ❤️",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { uriHandler.openUri("https://github.com/Aspharier") }
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            painter = androidx.compose.ui.res.painterResource(
                                id = com.questlife.app.R.drawable.ic_github
                            ),
                            contentDescription = "GitHub",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Aspharier",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// ── Minimal section label ──────────────────────────────────────────────────────

@Composable
private fun SectionLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    )
}

// ── Flat settings row (clickable) ──────────────────────────────────────────────

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

// ── Flat settings toggle row ───────────────────────────────────────────────────

@Composable
private fun SettingsToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

// ── Compact circular theme swatch ──────────────────────────────────────────────

@Composable
private fun ThemeSwatch(
    type: ThemeType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val swatchColors = themeSwatches(type)
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else Color.Transparent,
        animationSpec = tween(200),
        label = "swatchBorder"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        // Swatch circle
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) 2.5.dp else 0.dp,
                    color = borderColor,
                    shape = CircleShape
                )
                .padding(if (isSelected) 3.dp else 0.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(swatchColors),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(6.dp))

        // Theme short name
        Text(
            text = type.shortName,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

// ── Theme color swatch definitions ─────────────────────────────────────────────

private fun themeSwatches(type: ThemeType): List<Color> =
    when (type) {
        ThemeType.DEEP_DARK ->
            listOf(Color(0xFF0A0E1A), Color(0xFF818CF8), Color(0xFFF472B6))
        ThemeType.MYSTIC_PURPLE ->
            listOf(Color(0xFF130B1E), Color(0xFFC084FC), Color(0xFFF472B6))
        ThemeType.DARK_GREEN ->
            listOf(Color(0xFF061510), Color(0xFF4ADE80), Color(0xFFFBBF24))
        ThemeType.CRIMSON_NIGHT ->
            listOf(Color(0xFF140A0A), Color(0xFFEF4444), Color(0xFFF97316))
        ThemeType.OCEAN_DEPTHS ->
            listOf(Color(0xFF08121A), Color(0xFF06B6D4), Color(0xFF3B82F6))
        ThemeType.SUNSET_BLAZE ->
            listOf(Color(0xFF1A0F0A), Color(0xFFF97316), Color(0xFFD946EF))
        ThemeType.NEON_CYBER ->
            listOf(Color(0xFF060514), Color(0xFF2DD4BF), Color(0xFFF472B6))
        ThemeType.AURORA_GLADE ->
            listOf(Color(0xFFF7FBF7), Color(0xFF0F766E), Color(0xFFBE123C))
        ThemeType.PAPER_LANTERN ->
            listOf(Color(0xFFFFF8EA), Color(0xFFC2410C), Color(0xFF4338CA))
        ThemeType.SKY_CITADEL ->
            listOf(Color(0xFFF3F8FF), Color(0xFF0369A1), Color(0xFFA16207))
    }
