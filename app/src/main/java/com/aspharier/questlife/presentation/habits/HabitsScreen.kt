package com.aspharier.questlife.presentation.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspharier.questlife.core.ui.animations.FadeInEntrance
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.domain.model.HabitWithStreak
import com.aspharier.questlife.presentation.screens.GameScreenBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(viewModel: HabitsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val completionState by viewModel.completionState.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }
    val gameColors = LocalGameColors.current

    GameScreenBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> LoadingState()
                uiState.error != null -> ErrorState(message = uiState.error!!)
                uiState.habits.isEmpty() -> EmptyState()
            else -> {
                HabitList(
                        habits = uiState.habits,
                        completionState = completionState,
                        onComplete = { habitWithStreak ->
                            viewModel.onEvent(HabitsEvent.CompleteHabit(habitWithStreak.habit))
                        },
                        onDeactivate = { habitId ->
                            viewModel.onEvent(HabitsEvent.DeactivateHabit(habitId))
                        },
                        onClearCompletion = { viewModel.clearCompletionState() }
                )
            }
        }

        // Game-style FAB — fixed onClick handler
        Box(
                modifier =
                        Modifier.align(Alignment.BottomEnd)
                                .padding(20.dp)
                                .bounceClickable { showSheet = true }
                                .shadow(
                                        elevation = 12.dp,
                                        shape = CircleShape,
                                        spotColor = gameColors.fabGlow.copy(alpha = 0.5f)
                                )
                                .size(56.dp)
                                .background(
                                        Brush.radialGradient(
                                                colors =
                                                        listOf(
                                                                gameColors.fabGlow,
                                                                gameColors.fabGlow.copy(
                                                                        alpha = 0.8f
                                                                )
                                                        )
                                        ),
                                        CircleShape
                                ),
                contentAlignment = Alignment.Center
        ) { Text("⚔️", fontSize = 24.sp) }

        if (showSheet) {
            ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    containerColor = MaterialTheme.colorScheme.surface
            ) {
                CreateHabitSheet(
                        onCreate = { event ->
                            viewModel.onEvent(event)
                            showSheet = false
                        },
                        onDismiss = { showSheet = false }
                )
            }
        }
        }
    }
}

@Composable
private fun HabitList(
        habits: List<HabitWithStreak>,
        completionState: HabitCompletionState,
        onComplete: (HabitWithStreak) -> Unit,
        onDeactivate: (String) -> Unit,
        onClearCompletion: () -> Unit
) {
    LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            GameSectionHeader(
                    title = "Today's Habits",
                    modifier = Modifier.padding(top = 12.dp)
            )
        }

        itemsIndexed(items = habits, key = { _, it -> it.habit.id }) { index, habitWithStreak ->
            FadeInEntrance(index = index) {
                AnimatedHabitCard(
                        habitName = habitWithStreak.habit.name,
                        meta =
                                "${habitWithStreak.habit.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }} · ${habitWithStreak.habit.category.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        isCompleted = completionState.completedHabitId == habitWithStreak.habit.id,
                        isCompletedToday = habitWithStreak.isCompletedToday,
                        streak = habitWithStreak.currentStreak,
                        xpGained =
                                if (completionState.completedHabitId == habitWithStreak.habit.id)
                                        completionState.xpGained
                                else null,
                        onAnimationEnd = onClearCompletion,
                        onClick = {
                            if (!habitWithStreak.isCompletedToday) onComplete(habitWithStreak)
                        },
                        onLongPress = { onDeactivate(habitWithStreak.habit.id) }
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
                text = "No habits for today.\nTap ⚔️ to create your first quest!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}
