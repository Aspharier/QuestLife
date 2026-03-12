package com.aspharier.questlife.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aspharier.questlife.core.ui.animations.FadeInEntrance
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.navigation.NavRoute
import com.aspharier.questlife.presentation.habits.AnimatedHabitCard
import com.aspharier.questlife.presentation.habits.CreateHabitSheet
import com.aspharier.questlife.presentation.habits.HabitsEvent
import com.aspharier.questlife.presentation.habits.HabitsViewModel
import com.aspharier.questlife.presentation.profile.LevelUpAnimation
import com.aspharier.questlife.presentation.profile.ProfileViewModel
import com.aspharier.questlife.presentation.quests.QuestCard
import com.aspharier.questlife.presentation.quests.QuestsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val habitsViewModel: HabitsViewModel = hiltViewModel()
        val questsViewModel: QuestsViewModel = hiltViewModel()

        val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()
        val habitsState by habitsViewModel.uiState.collectAsStateWithLifecycle()
        val completionState by habitsViewModel.completionState.collectAsStateWithLifecycle()
        val questsState by questsViewModel.uiState.collectAsStateWithLifecycle()

        var showSheet by remember { mutableStateOf(false) }
        val gameColors = LocalGameColors.current

        Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 96.dp)
                ) {
                        item {
                                Spacer(Modifier.height(8.dp))
                                AvatarHeroSection(
                                        level = profileState.level,
                                        totalXp = profileState.totalXp,
                                        progress = profileState.progressToNextLevel,
                                        persona = profileState.persona,
                                        onAvatarClick = { navController.navigate(NavRoute.Roadmap.route) }
                                )
                        }

                        item {
                                Spacer(Modifier.height(12.dp))
                                StatsRow(
                                        hp = profileState.stats.hp,
                                        atk = profileState.stats.atk,
                                        def = profileState.stats.def,
                                        mana = profileState.stats.mana,
                                        luck = profileState.stats.luck
                                )
                        }

                        item {
                                Spacer(Modifier.height(20.dp))
                                if (questsState.dailyQuests.isNotEmpty()) {
                                        GameSectionHeader(title = "Daily Quests", icon = "📜")
                                        Spacer(Modifier.height(8.dp))
                                        questsState.dailyQuests.forEachIndexed { index, quest ->
                                                FadeInEntrance(index = index) {
                                                        Box(
                                                                modifier =
                                                                        Modifier.padding(
                                                                                horizontal = 16.dp,
                                                                                vertical = 4.dp
                                                                        )
                                                        ) { QuestCard(quest = quest) }
                                                }
                                        }
                                        Spacer(Modifier.height(16.dp))
                                }

                                GameSectionHeader(title = "Today's Habits", icon = "⚔️")
                        }

                        if (habitsState.habits.isEmpty() && !habitsState.isLoading) {
                                item {
                                        Spacer(Modifier.height(24.dp))
                                        Text(
                                                text =
                                                        "No habits for today.\nTap ⚔️ to create your first quest!",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                }
                        }

                        items(habitsState.habits.take(5), key = { it.habit.id }) { habitWithStreak
                                ->
                                val index = habitsState.habits.indexOf(habitWithStreak)
                                FadeInEntrance(index = index + questsState.dailyQuests.size) {
                                        Spacer(Modifier.height(8.dp))
                                        AnimatedHabitCard(
                                                habitName = habitWithStreak.habit.name,
                                                meta =
                                                        "${habitWithStreak.habit.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }} · ${habitWithStreak.habit.category.name.lowercase().replaceFirstChar { it.uppercase() }}",
                                                isCompleted =
                                                        completionState.completedHabitId ==
                                                                habitWithStreak.habit.id,
                                                isCompletedToday = habitWithStreak.isCompletedToday,
                                                streak = habitWithStreak.currentStreak,
                                                xpGained =
                                                        if (completionState.completedHabitId ==
                                                                        habitWithStreak.habit.id
                                                        )
                                                                completionState.xpGained
                                                        else null,
                                                onAnimationEnd = {
                                                        habitsViewModel.clearCompletionState()
                                                },
                                                onClick = {
                                                        if (!habitWithStreak.isCompletedToday)
                                                                habitsViewModel.onEvent(
                                                                        HabitsEvent.CompleteHabit(
                                                                                habitWithStreak
                                                                                        .habit
                                                                        )
                                                                )
                                                },
                                                onLongPress = {}
                                        )
                                }
                        }
                }

                // Level-up overlay
                if (profileState.levelUp) {
                        LevelUpAnimation(level = profileState.level)
                }

                // Game-style FAB
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
                                                habitsViewModel.onEvent(event)
                                                showSheet = false
                                        },
                                        onDismiss = { showSheet = false }
                                )
                        }
                }
        }
}
