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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(
                "Create Quest",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        // Name
        OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Habit Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Description (optional)
        OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(20.dp))

        // Category
        Text(
                "Category",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
        ) {
            HabitCategory.values().forEach { cat ->
                val selected = category == cat
                ChipButton(
                        label =
                                "${categoryEmoji(cat)} ${cat.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        selected = selected,
                        color = categoryColorCompose(cat),
                        onClick = { category = cat }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Difficulty
        Text(
                "Difficulty",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HabitDifficulty.values().forEach { diff ->
                val selected = difficulty == diff
                ChipButton(
                        label =
                                "${difficultyEmoji(diff)} ${diff.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        selected = selected,
                        color = difficultyColor(diff),
                        onClick = { difficulty = diff }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Frequency
        Text(
                "Frequency",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(HabitFrequency.DAILY to "Daily", HabitFrequency.WEEKLY to "Specific Days")
                    .forEach { (freq, label) ->
                        ChipButton(
                                label = label,
                                selected = frequency == freq,
                                color = MaterialTheme.colorScheme.primary,
                                onClick = {
                                    frequency = freq
                                    if (freq == HabitFrequency.DAILY) selectedDays = emptySet()
                                }
                        )
                    }
        }

        // Day selector (only for WEEKLY)
        AnimatedVisibility(
                visible = frequency == HabitFrequency.WEEKLY,
                enter = expandVertically(),
                exit = shrinkVertically()
        ) {
            Column {
                Spacer(Modifier.height(12.dp))
                Text("Repeat On", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(8.dp))
                Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEachIndexed { _, day
                        ->
                        val fullDay = dayFullName(day)
                        val selected = selectedDays.contains(fullDay)
                        DayCircle(
                                label = day,
                                selected = selected,
                                onClick = {
                                    selectedDays =
                                            if (selected) selectedDays - fullDay
                                            else selectedDays + fullDay
                                }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val freqDays =
                                if (frequency == HabitFrequency.WEEKLY) selectedDays.toList()
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
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp)
        ) { Text("Create Quest ⚔️", fontSize = 16.sp, fontWeight = FontWeight.Bold) }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun ChipButton(label: String, selected: Boolean, color: Color, onClick: () -> Unit) {
    Box(
            modifier =
                    Modifier.clip(RoundedCornerShape(20.dp))
                            .border(
                                    1.dp,
                                    if (selected) color else MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(20.dp)
                            )
                            .background(
                                    if (selected) color.copy(alpha = 0.15f) else Color.Transparent
                            )
                            .clickable(onClick = onClick)
                            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) color else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun DayCircle(label: String, selected: Boolean, onClick: () -> Unit) {
    val color = MaterialTheme.colorScheme.primary
    Box(
            modifier =
                    Modifier.size(38.dp)
                            .clip(CircleShape)
                            .background(if (selected) color else color.copy(alpha = 0.1f))
                            .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else color
        )
    }
}

private fun dayFullName(abbr: String) =
        when (abbr) {
            "MON" -> "MONDAY"
            "TUE" -> "TUESDAY"
            "WED" -> "WEDNESDAY"
            "THU" -> "THURSDAY"
            "FRI" -> "FRIDAY"
            "SAT" -> "SATURDAY"
            else -> "SUNDAY"
        }

private fun categoryEmoji(category: HabitCategory) =
        when (category) {
            HabitCategory.HEALTH -> "❤️"
            HabitCategory.FITNESS -> "💪"
            HabitCategory.PRODUCTIVITY -> "🚀"
            HabitCategory.LEARNING -> "📚"
            HabitCategory.SOCIAL -> "🤝"
            HabitCategory.CREATIVE -> "🎨"
        }

@Composable
private fun categoryColorCompose(category: HabitCategory): Color =
        when (category) {
            HabitCategory.HEALTH -> Color(0xFF22C55E)
            HabitCategory.FITNESS -> Color(0xFFF97316)
            HabitCategory.PRODUCTIVITY -> MaterialTheme.colorScheme.primary
            HabitCategory.LEARNING -> Color(0xFF3B82F6)
            HabitCategory.SOCIAL -> Color(0xFFEC4899)
            HabitCategory.CREATIVE -> Color(0xFF8B5CF6)
        }

private fun difficultyEmoji(difficulty: HabitDifficulty) =
        when (difficulty) {
            HabitDifficulty.EASY -> "🌱"
            HabitDifficulty.MEDIUM -> "⚡"
            HabitDifficulty.HARD -> "🔥"
        }

private fun difficultyColor(difficulty: HabitDifficulty) =
        when (difficulty) {
            HabitDifficulty.EASY -> Color(0xFF22C55E)
            HabitDifficulty.MEDIUM -> Color(0xFFFBBF24)
            HabitDifficulty.HARD -> Color(0xFFEF4444)
        }
