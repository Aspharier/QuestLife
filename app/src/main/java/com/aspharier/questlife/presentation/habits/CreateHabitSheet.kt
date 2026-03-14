package com.aspharier.questlife.presentation.habits

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.animations.FadeInEntrance
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.domain.model.HabitCategory
import com.aspharier.questlife.domain.model.HabitDifficulty
import com.aspharier.questlife.domain.model.HabitFrequency

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateHabitSheet(onCreate: (HabitsEvent.CreateHabit) -> Unit, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf(HabitDifficulty.EASY) }
    var category by remember { mutableStateOf(HabitCategory.PRODUCTIVITY) }
    var frequency by remember { mutableStateOf(HabitFrequency.DAILY) }
    var selectedDays by remember { mutableStateOf(setOf<String>()) }
    var description by remember { mutableStateOf("") }
    val gameColors = LocalGameColors.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding() + 48.dp)
    ) {
        FadeInEntrance(index = 0) {
            GameSectionHeader(
                title = "New Quest",
                icon = "⚔️",
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Spacer(Modifier.height(16.dp))

            // Name
            FadeInEntrance(index = 1) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Quest Name", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)) },
                    placeholder = { Text("e.g., Slay the Dragon of Procrastination", fontSize = 14.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = gameColors.panelBorder,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            // Description (optional)
            FadeInEntrance(index = 2) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)) },
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = gameColors.panelBorder,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                    )
                )
            }

            Spacer(Modifier.height(24.dp))

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = gameColors.panelBorder.copy(alpha = 0.2f)
            )

            // Category
            FadeInEntrance(index = 3) {
                Column {
                    Text(
                        "Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HabitCategory.entries.forEach { cat ->
                            val selected = category == cat
                            GameChipButton(
                                label = "${categoryEmoji(cat)} ${cat.name.lowercase().replaceFirstChar { it.uppercase() }}",
                                selected = selected,
                                color = categoryColorCompose(cat),
                                onClick = { category = cat }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Difficulty
            FadeInEntrance(index = 4) {
                Column {
                    Text(
                        "Difficulty",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HabitDifficulty.entries.forEach { diff ->
                            val selected = difficulty == diff
                            Box(modifier = Modifier.weight(1f)) {
                                GameChipButton(
                                    label = "${difficultyEmoji(diff)} ${diff.name.lowercase().replaceFirstChar { it.uppercase() }}",
                                    selected = selected,
                                    color = difficultyColor(diff),
                                    onClick = { difficulty = diff },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Frequency
            FadeInEntrance(index = 5) {
                Column {
                    Text(
                        "Frequency",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(
                            HabitFrequency.DAILY to "Daily",
                            HabitFrequency.WEEKLY to "Specific"
                        ).forEach { (freq, label) ->
                            Box(modifier = Modifier.weight(1f)) {
                                GameChipButton(
                                    label = label,
                                    selected = frequency == freq,
                                    color = MaterialTheme.colorScheme.primary,
                                    onClick = {
                                        frequency = freq
                                        if (freq == HabitFrequency.DAILY) selectedDays = emptySet()
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    // Day selector (only for WEEKLY)
                    AnimatedVisibility(
                        visible = frequency == HabitFrequency.WEEKLY,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column {
                            Spacer(Modifier.height(16.dp))
                            GamePanel(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = gameColors.panelBorder.copy(alpha = 0.5f),
                                cornerRadius = 12.dp
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach { day ->
                                        val fullDay = dayFullName(day)
                                        val selected = selectedDays.contains(fullDay)
                                        DayCircle(
                                            label = day,
                                            selected = selected,
                                            onClick = {
                                                selectedDays = if (selected) selectedDays - fullDay
                                                else selectedDays + fullDay
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Create Button
            FadeInEntrance(index = 6) {
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            val freqDays = if (frequency == HabitFrequency.WEEKLY) selectedDays.toList()
                            else emptyList()
                            onCreate(
                                HabitsEvent.CreateHabit(
                                    name = name.trim(),
                                    difficulty = difficulty,
                                    category = category,
                                    frequency = frequency,
                                    frequencyDays = freqDays,
                                    description = description.ifBlank { null }
                                )
                            )
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .bounceClickable(enabled = name.isNotBlank()) { },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    ),
                    enabled = name.isNotBlank()
                ) {
                    Text(
                        "Forge Quest ⚔️",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun GameChipButton(
    label: String,
    selected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gameColors = LocalGameColors.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(
                2.dp,
                if (selected) color else gameColors.panelBorder.copy(alpha = 0.5f),
                RoundedCornerShape(14.dp)
            )
            .background(
                if (selected) color.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) color else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium
        )
    }
}

@Composable
private fun DayCircle(label: String, selected: Boolean, onClick: () -> Unit) {
    val color = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                if (selected) color 
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .border(
                1.dp,
                if (selected) Color.White.copy(alpha = 0.5f) else Color.Transparent,
                CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label.first().toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) Color.White else color.copy(alpha = 0.7f)
        )
    }
}

private fun dayFullName(abbr: String) = when (abbr) {
    "MON" -> "MONDAY"
    "TUE" -> "TUESDAY"
    "WED" -> "WEDNESDAY"
    "THU" -> "THURSDAY"
    "FRI" -> "FRIDAY"
    "SAT" -> "SATURDAY"
    else -> "SUNDAY"
}

private fun categoryEmoji(category: HabitCategory) = when (category) {
    HabitCategory.HEALTH -> "❤️"
    HabitCategory.FITNESS -> "💪"
    HabitCategory.PRODUCTIVITY -> "🚀"
    HabitCategory.LEARNING -> "📚"
    HabitCategory.SOCIAL -> "🤝"
    HabitCategory.CREATIVE -> "🎨"
}

@Composable
private fun categoryColorCompose(category: HabitCategory): Color = when (category) {
    HabitCategory.HEALTH -> Color(0xFF22C55E)
    HabitCategory.FITNESS -> Color(0xFFF97316)
    HabitCategory.PRODUCTIVITY -> MaterialTheme.colorScheme.primary
    HabitCategory.LEARNING -> Color(0xFF3B82F6)
    HabitCategory.SOCIAL -> Color(0xFFEC4899)
    HabitCategory.CREATIVE -> Color(0xFF8B5CF6)
}

private fun difficultyEmoji(difficulty: HabitDifficulty) = when (difficulty) {
    HabitDifficulty.EASY -> "🌱"
    HabitDifficulty.MEDIUM -> "⚡"
    HabitDifficulty.HARD -> "🔥"
    HabitDifficulty.VERY_HARD -> "💀"
    HabitDifficulty.EXTREME -> "👑"
}

private fun difficultyColor(difficulty: HabitDifficulty) = when (difficulty) {
    HabitDifficulty.EASY -> Color(0xFF22C55E)
    HabitDifficulty.MEDIUM -> Color(0xFFFBBF24)
    HabitDifficulty.HARD -> Color(0xFFEF4444)
    HabitDifficulty.VERY_HARD -> Color(0xFF8B5CF6)
    HabitDifficulty.EXTREME -> Color(0xFFFFD700)
}
