package com.aspharier.questlife.presentation.habits

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.animations.bounceClickable
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
    var currentStep by remember { mutableIntStateOf(0) }
    val gameColors = LocalGameColors.current
    val primaryColor = MaterialTheme.colorScheme.primary

    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "forgeAnim")
    val headerGlow by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "headerGlow"
    )
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -3f, targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "float"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        // ═══════════ ANIMATED HEADER ═══════════
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.15f),
                            primaryColor.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Floating forge icon
                Text(
                    text = "⚒️",
                    fontSize = 40.sp,
                    modifier = Modifier.graphicsLayer {
                        translationY = floatOffset.dp.toPx()
                    }
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "QUEST FORGE",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 4.sp,
                    color = primaryColor
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Craft your next adventure",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // ═══════════ STEP INDICATOR ═══════════
        StepIndicator(
            currentStep = currentStep,
            totalSteps = 3,
            primaryColor = primaryColor,
            glowAlpha = headerGlow
        )

        Spacer(Modifier.height(20.dp))

        // ═══════════ STEP CONTENT WITH ANIMATED TRANSITIONS ═══════════
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { it } + fadeIn())
                        .togetherWith(slideOutHorizontally { -it } + fadeOut())
                } else {
                    (slideInHorizontally { -it } + fadeIn())
                        .togetherWith(slideOutHorizontally { it } + fadeOut())
                }.using(SizeTransform(clip = false))
            },
            label = "stepContent"
        ) { step ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                when (step) {
                    0 -> StepNameAndDescription(
                        name = name,
                        onNameChange = { name = it },
                        description = description,
                        onDescriptionChange = { description = it },
                        gameColors = gameColors,
                        primaryColor = primaryColor
                    )
                    1 -> StepCategoryAndDifficulty(
                        category = category,
                        onCategoryChange = { category = it },
                        difficulty = difficulty,
                        onDifficultyChange = { difficulty = it },
                        primaryColor = primaryColor
                    )
                    2 -> StepFrequency(
                        frequency = frequency,
                        onFrequencyChange = {
                            frequency = it
                            if (it == HabitFrequency.DAILY) selectedDays = emptySet()
                        },
                        selectedDays = selectedDays,
                        onDaysChange = { selectedDays = it },
                        name = name,
                        category = category,
                        difficulty = difficulty,
                        primaryColor = primaryColor,
                        gameColors = gameColors
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ═══════════ NAVIGATION BUTTONS ═══════════
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Back button
            if (currentStep > 0) {
                OutlinedButton(
                    onClick = { currentStep-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = ButtonDefaults.outlinedButtonBorder,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor
                    )
                ) {
                    Text(
                        "← Back",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            // Next / Forge button
            val isLastStep = currentStep == 2
            val canProceed = when (currentStep) {
                0 -> name.isNotBlank()
                else -> true
            }

            Button(
                onClick = {
                    if (isLastStep && canProceed) {
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
                    } else if (!isLastStep && canProceed) {
                        currentStep++
                    }
                },
                modifier = Modifier
                    .weight(if (currentStep > 0) 1.5f else 1f)
                    .height(52.dp)
                    .then(
                        if (canProceed && isLastStep)
                            Modifier.shadow(
                                8.dp,
                                RoundedCornerShape(16.dp),
                                spotColor = primaryColor.copy(alpha = 0.5f)
                            )
                        else Modifier
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLastStep) primaryColor else primaryColor.copy(alpha = 0.9f),
                    disabledContainerColor = primaryColor.copy(alpha = 0.3f)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = if (isLastStep) 12.dp else 4.dp,
                    pressedElevation = 2.dp
                ),
                enabled = canProceed
            ) {
                Text(
                    text = if (isLastStep) "⚔️ Forge Quest" else "Next →",
                    fontSize = if (isLastStep) 17.sp else 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = if (isLastStep) 1.sp else 0.sp
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

// ═══════════════════════════════════════════════
// STEP INDICATOR
// ═══════════════════════════════════════════════

@Composable
private fun StepIndicator(
    currentStep: Int,
    totalSteps: Int,
    primaryColor: Color,
    glowAlpha: Float
) {
    val stepLabels = listOf("Name", "Style", "Schedule")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalSteps) {
            val isActive = i <= currentStep
            val isCurrent = i == currentStep

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                // Orb
                Box(
                    modifier = Modifier
                        .size(if (isCurrent) 36.dp else 28.dp)
                        .then(
                            if (isCurrent) Modifier.shadow(
                                12.dp, CircleShape,
                                spotColor = primaryColor.copy(alpha = glowAlpha)
                            ) else Modifier
                        )
                        .clip(CircleShape)
                        .background(
                            if (isActive)
                                Brush.radialGradient(
                                    colors = listOf(
                                        primaryColor,
                                        primaryColor.copy(alpha = 0.7f)
                                    )
                                )
                            else Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            )
                        )
                        .then(
                            if (isCurrent) Modifier.border(
                                2.dp,
                                primaryColor.copy(alpha = glowAlpha),
                                CircleShape
                            ) else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${i + 1}",
                        color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isCurrent) 14.sp else 12.sp
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = stepLabels[i],
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isActive) primaryColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 11.sp
                )
            }

            // Connecting line
            if (i < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(
                            if (i < currentStep) primaryColor.copy(alpha = 0.6f)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════
// STEP 1: NAME & DESCRIPTION
// ═══════════════════════════════════════════════

@Composable
private fun StepNameAndDescription(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    gameColors: com.aspharier.questlife.core.ui.theme.GameColors,
    primaryColor: Color
) {
    Column {
        SectionLabel(icon = "📜", title = "Quest Name")
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = {
                Text(
                    "e.g., Slay the Dragon of Procrastination",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = gameColors.panelBorder.copy(alpha = 0.4f),
                cursorColor = primaryColor,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        )

        Spacer(Modifier.height(20.dp))

        SectionLabel(icon = "✏️", title = "Description (optional)")
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            placeholder = {
                Text(
                    "What does this quest involve?",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = gameColors.panelBorder.copy(alpha = 0.4f),
                cursorColor = primaryColor,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        )

        Spacer(Modifier.height(16.dp))
    }
}

// ═══════════════════════════════════════════════
// STEP 2: CATEGORY & DIFFICULTY
// ═══════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StepCategoryAndDifficulty(
    category: HabitCategory,
    onCategoryChange: (HabitCategory) -> Unit,
    difficulty: HabitDifficulty,
    onDifficultyChange: (HabitDifficulty) -> Unit,
    primaryColor: Color
) {
    Column {
        SectionLabel(icon = "🎭", title = "Category")
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            HabitCategory.entries.forEach { cat ->
                val selected = category == cat
                val color = categoryColorCompose(cat)
                SelectionCard(
                    icon = categoryEmoji(cat),
                    label = cat.name.lowercase().replaceFirstChar { it.uppercase() },
                    selected = selected,
                    accentColor = color,
                    onClick = { onCategoryChange(cat) }
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        SectionLabel(icon = "⚡", title = "Difficulty")
        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            HabitDifficulty.entries.forEach { diff ->
                val selected = difficulty == diff
                val color = difficultyColor(diff)
                SelectionCard(
                    icon = difficultyEmoji(diff),
                    label = diff.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() },
                    selected = selected,
                    accentColor = color,
                    onClick = { onDifficultyChange(diff) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ═══════════════════════════════════════════════
// STEP 3: FREQUENCY
// ═══════════════════════════════════════════════

@Composable
private fun StepFrequency(
    frequency: HabitFrequency,
    onFrequencyChange: (HabitFrequency) -> Unit,
    selectedDays: Set<String>,
    onDaysChange: (Set<String>) -> Unit,
    name: String,
    category: HabitCategory,
    difficulty: HabitDifficulty,
    @Suppress("unused") primaryColor: Color,
    gameColors: com.aspharier.questlife.core.ui.theme.GameColors
) {
    Column {
        SectionLabel(icon = "📅", title = "Frequency")
        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf(
                HabitFrequency.DAILY to "☀️ Daily",
                HabitFrequency.WEEKLY to "📋 Specific Days"
            ).forEach { (freq, label) ->
                Box(modifier = Modifier.weight(1f)) {
                    FrequencyChip(
                        label = label,
                        selected = frequency == freq,
                        color = primaryColor,
                        onClick = { onFrequencyChange(freq) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Day selector for WEEKLY
        AnimatedVisibility(
            visible = frequency == HabitFrequency.WEEKLY,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                        .border(
                            1.dp,
                            gameColors.panelBorder.copy(alpha = 0.3f),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(14.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach { day ->
                            val fullDay = dayFullName(day)
                            val selected = selectedDays.contains(fullDay)
                            DayCircle(
                                label = day,
                                selected = selected,
                                onClick = {
                                    onDaysChange(
                                        if (selected) selectedDays - fullDay
                                        else selectedDays + fullDay
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Quest Summary Preview
        if (name.isNotBlank()) {
            SectionLabel(icon = "📋", title = "Quest Summary")
            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.08f),
                                primaryColor.copy(alpha = 0.03f)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        primaryColor.copy(alpha = 0.2f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SummaryTag(
                            text = "${categoryEmoji(category)} ${category.name.lowercase().replaceFirstChar { it.uppercase() }}",
                            color = categoryColorCompose(category)
                        )
                        SummaryTag(
                            text = "${difficultyEmoji(difficulty)} ${difficulty.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() }}",
                            color = difficultyColor(difficulty)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = if (frequency == HabitFrequency.DAILY) "☀️ Every day"
                        else "📋 ${selectedDays.joinToString(", ") { it.lowercase().replaceFirstChar { c -> c.uppercase() } }.ifEmpty { "Select days" }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ═══════════════════════════════════════════════
// SHARED UI COMPONENTS
// ═══════════════════════════════════════════════

@Composable
private fun SectionLabel(icon: String, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = icon, fontSize = 18.sp)
        Spacer(Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SelectionCard(
    icon: String,
    label: String,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    val gameColors = LocalGameColors.current

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .then(
                if (selected) Modifier
                    .shadow(
                        4.dp,
                        RoundedCornerShape(14.dp),
                        spotColor = accentColor.copy(alpha = 0.3f)
                    )
                    .border(
                        2.dp,
                        Brush.linearGradient(
                            colors = listOf(
                                accentColor,
                                accentColor.copy(alpha = 0.5f)
                            )
                        ),
                        RoundedCornerShape(14.dp)
                    )
                    .background(accentColor.copy(alpha = 0.12f))
                else Modifier
                    .border(
                        1.5.dp,
                        gameColors.panelBorder.copy(alpha = 0.3f),
                        RoundedCornerShape(14.dp)
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 16.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun FrequencyChip(
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
            .then(
                if (selected) Modifier
                    .border(2.dp, color, RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.12f))
                else Modifier
                    .border(1.5.dp, gameColors.panelBorder.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) color else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
            textAlign = TextAlign.Center
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

@Composable
private fun SummaryTag(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ═══════════════════════════════════════════════
// UTILITY FUNCTIONS
// ═══════════════════════════════════════════════

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
