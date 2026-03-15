package com.aspharier.questlife.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aspharier.questlife.core.ai.AIMotivationSystem
import com.aspharier.questlife.core.ui.animations.*
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.core.ui.theme.TimeThemeSystem
import com.aspharier.questlife.navigation.NavRoute
import com.aspharier.questlife.presentation.achievements.AchievementUnlockAnimation
import com.aspharier.questlife.presentation.companion.AnimatedCompanion
import com.aspharier.questlife.presentation.companion.CompanionType
import com.aspharier.questlife.presentation.companion.getCompanionForClass
import com.aspharier.questlife.presentation.habits.AnimatedHabitCard
import com.aspharier.questlife.presentation.habits.CreateHabitSheet
import com.aspharier.questlife.presentation.habits.HabitsEvent
import com.aspharier.questlife.presentation.habits.HabitsViewModel
import com.aspharier.questlife.presentation.profile.LevelUpAnimation
import com.aspharier.questlife.presentation.profile.ProfileViewModel
import com.aspharier.questlife.presentation.quests.QuestCard
import com.aspharier.questlife.presentation.quests.QuestsViewModel
import com.aspharier.questlife.domain.model.Quest
import com.aspharier.questlife.presentation.rewards.ComboIndicator

import com.aspharier.questlife.domain.model.Achievement
import kotlinx.coroutines.delay

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

        var aiMessage by remember { mutableStateOf(AIMotivationSystem.getTimeBasedMessage()) }
        var comboCount by remember { mutableIntStateOf(0) }
        var comboTimeRemaining by remember { mutableLongStateOf(0L) }
        var achievementsToShow by remember { mutableStateOf<List<Achievement>>(emptyList()) }

        val gameColors = LocalGameColors.current
        val currentTheme = TimeThemeSystem.getCurrentTheme()

        val companionType = remember(profileState.persona.avatarClass) {
                getCompanionForClass(profileState.persona.avatarClass.name)
        }

        // AI message rotation
        LaunchedEffect(habitsState.habits) {
                delay(30000)
                aiMessage = AIMotivationSystem.getTimeBasedMessage()
        }

        Box(modifier = Modifier.fillMaxSize()) {
                // Time-themed gradient background
                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(
                                        Brush.verticalGradient(
                                                colors = currentTheme.backgroundGradient
                                        )
                                )
                )

                // Ambient particles
                GlowingBackground(
                        primaryColor = currentTheme.primaryColor,
                        secondaryColor = currentTheme.secondaryColor
                )
                AmbientParticles(
                        colors = currentTheme.particleColors,
                        particleCount = 25
                )

                LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 96.dp)
                ) {
                        item {
                                Spacer(Modifier.height(8.dp))

                                // AI Motivation Banner
                                AnimatedVisibility(
                                        visible = true,
                                        enter = fadeIn(),
                                        exit = fadeOut()
                                ) {
                                        AIMessageBanner(
                                                message = aiMessage,
                                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                        )
                                }

                                // Companion + Avatar section
                                AvatarHeroSection(
                                        level = profileState.level,
                                        totalXp = profileState.totalXp,
                                        progress = profileState.progressToNextLevel,
                                        persona = profileState.persona,
                                        companionType = companionType,
                                        onAvatarClick = { navController.navigate(NavRoute.Roadmap.route) }
                                )
                        }

                        item {
                                Spacer(Modifier.height(12.dp))

                                // Combo indicator
                                if (comboCount >= 2) {
                                        ComboIndicator(
                                                comboCount = comboCount,
                                                timeRemaining = comboTimeRemaining,
                                                modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                }

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
                                        GameSectionHeader(title = "Daily Quests", icon = "📜", textColor = Color.White)
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

                                GameSectionHeader(title = "Today's Habits", icon = "⚔️", textColor = Color.White)
                        }

                        if (habitsState.habits.isEmpty() && !habitsState.isLoading) {
                                item {
                                        Spacer(Modifier.height(24.dp))
                                        Text(
                                                text =
                                                        "No habits for today.\nTap ⚔️ to create your first quest!",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = Color.White.copy(alpha = 0.8f),
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

                // Floating Action Buttons - positioned at bottom end
                Column(
                        modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 16.dp, bottom = 16.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                        // Main Create Quest FAB
                        Box(
                                modifier = Modifier
                                        .bounceClickable { showSheet = true }
                                        .shadow(
                                                elevation = 16.dp,
                                                shape = RoundedCornerShape(28.dp),
                                                spotColor = gameColors.fabGlow.copy(alpha = 0.6f)
                                        )
                                        .background(
                                                Brush.horizontalGradient(
                                                        colors = listOf(
                                                                gameColors.fabGlow,
                                                                gameColors.fabGlow.copy(alpha = 0.7f)
                                                        )
                                                ),
                                                RoundedCornerShape(28.dp)
                                        )
                                        .padding(horizontal = 24.dp, vertical = 14.dp),
                                contentAlignment = Alignment.Center
                        ) {
                                Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                ) {
                                        Text("⚔️", fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                                "New Quest",
                                                style = MaterialTheme.typography.titleSmall,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                        )
                                }
                        }
                }

                if (showSheet) {
                        androidx.compose.ui.window.Dialog(
                                onDismissRequest = { showSheet = false },
                                properties = androidx.compose.ui.window.DialogProperties(
                                        usePlatformDefaultWidth = false
                                )
                        ) {
                                Card(
                                        modifier = Modifier
                                                .fillMaxWidth(0.95f)
                                                .padding(vertical = 24.dp, horizontal = 16.dp),
                                        shape = RoundedCornerShape(24.dp),
                                        colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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

                // Achievement unlock animations
                if (achievementsToShow.isNotEmpty()) {
                        AchievementUnlockAnimation(
                                achievements = achievementsToShow,
                                onDismiss = { achievementsToShow = emptyList() }
                        )
                }
        }
}

@Composable
fun GameActionButton(
        icon: String,
        label: String,
        gradientColors: List<Color>,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
        Card(
                modifier = modifier
                        .bounceClickable { onClick() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
                Box(
                        modifier = Modifier
                                .background(
                                        Brush.horizontalGradient(colors = gradientColors),
                                        RoundedCornerShape(16.dp)
                                )
                                .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(icon, fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                        label,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                )
                        }
                }
        }
}

@Composable
fun SmallActionFab(
        icon: String,
        gradientColors: List<Color>,
        onClick: () -> Unit
) {
        Box(
                modifier = Modifier
                        .bounceClickable { onClick() }
                        .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                spotColor = gradientColors.first().copy(alpha = 0.5f)
                        )
                        .size(48.dp)
                        .background(
                                Brush.radialGradient(colors = gradientColors),
                                CircleShape
                        ),
                contentAlignment = Alignment.Center
        ) {
                Text(icon, fontSize = 20.sp)
        }
}

@Composable
fun AIMessageBanner(
        message: com.aspharier.questlife.core.ai.AIMessage,
        modifier: Modifier = Modifier
) {
        val infiniteTransition = rememberInfiniteTransition(label = "aiBanner")
        val glowAlpha by infiniteTransition.animateFloat(0.3f, 0.6f, infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
        ), label = "glowAlpha")

        Card(
                modifier = modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = glowAlpha)
                ),
                shape = RoundedCornerShape(16.dp)
        ) {
                Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        val moodEmoji = when (message.mood) {
                                com.aspharier.questlife.core.ai.AIMood.ENTHUSIASTIC -> "⚡"
                                com.aspharier.questlife.core.ai.AIMood.CALM -> "🌙"
                                com.aspharier.questlife.core.ai.AIMood.CHEERING -> "🎉"
                                com.aspharier.questlife.core.ai.AIMood.FOCUSED -> "🎯"
                                com.aspharier.questlife.core.ai.AIMood.MYSTERIOUS -> "✨"
                                com.aspharier.questlife.core.ai.AIMood.PLAYFUL -> "🤪"
                        }

                        Text(text = moodEmoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                                Text(
                                        text = "AI Companion",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                )
                                Text(
                                        text = message.text,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.9f)
                                )
                                if (message.xpBonus > 0) {
                                        Text(
                                                text = "+${message.xpBonus} XP bonus available!",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.tertiary
                                        )
                                }
                        }
                }
        }
}
