package com.aspharier.questlife.presentation.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspharier.questlife.domain.model.HabitWithStreak

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(viewModel: HabitsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val completionState by viewModel.completionState.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }

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

        FloatingActionButton(
                onClick = { showSheet = true },
                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
                containerColor = MaterialTheme.colorScheme.primary
        ) { Text("⚔️") }

        if (showSheet) {
            ModalBottomSheet(onDismissRequest = { showSheet = false }) {
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                    text = "Today's Habits",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
            )
        }

        items(items = habits, key = { it.habit.id }) { habitWithStreak ->
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

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
                text = "No habits for today.\nTap ⚔️ to create your first quest!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}
