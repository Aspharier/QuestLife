package com.aspharier.questlife.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspharier.questlife.presentation.habits.AnimatedHabitCard
import com.aspharier.questlife.presentation.habits.HabitsEvent
import com.aspharier.questlife.presentation.habits.HabitsViewModel
import com.aspharier.questlife.presentation.profile.LevelUpAnimation
import com.aspharier.questlife.presentation.profile.ProfileViewModel

@Composable
fun HomeScreen() {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val habitsViewModel: HabitsViewModel = hiltViewModel()

    val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()
    val habitsState by habitsViewModel.uiState.collectAsStateWithLifecycle()
    val completionState by habitsViewModel.completionState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            item {
                AvatarHeroSection(
                        level = profileState.level,
                        totalXp = profileState.totalXp,
                        progress = profileState.progressToNextLevel
                )
            }

            item {
                Spacer(Modifier.height(8.dp))
                StatsRow(
                        hp = profileState.stats.hp,
                        atk = profileState.stats.atk,
                        def = profileState.stats.def,
                        mana = profileState.stats.mana,
                        luck = profileState.stats.luck
                )
            }

            item {
                Spacer(Modifier.height(24.dp))
                Text(
                        text = "Today's Habits",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (habitsState.habits.isEmpty() && !habitsState.isLoading) {
                item {
                    Spacer(Modifier.height(24.dp))
                    Text(
                            text = "No habits for today.\nTap ⚔️ to create your first quest!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            items(habitsState.habits.take(5), key = { it.habit.id }) { habitWithStreak ->
                Spacer(Modifier.height(12.dp))
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
                        onAnimationEnd = { habitsViewModel.clearCompletionState() },
                        onClick = {
                            if (!habitWithStreak.isCompletedToday)
                                    habitsViewModel.onEvent(
                                            HabitsEvent.CompleteHabit(habitWithStreak.habit)
                                    )
                        },
                        onLongPress = {}
                )
            }
        }

        // Level-up overlay
        if (profileState.levelUp) {
            LevelUpAnimation(level = profileState.level)
        }

        // FAB
        FloatingActionButton(
                onClick = {},
                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
                containerColor = MaterialTheme.colorScheme.primary
        ) { Text("⚔️", fontSize = 22.sp) }
    }
}
