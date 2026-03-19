package com.aspharier.questlife.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

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
import androidx.compose.ui.text.style.TextAlign
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

import com.aspharier.questlife.presentation.profile.LevelUpAnimation
import com.aspharier.questlife.presentation.profile.ProfileViewModel
import com.aspharier.questlife.presentation.quests.QuestCard
import com.aspharier.questlife.presentation.quests.QuestsViewModel
import com.aspharier.questlife.domain.model.Quest
import com.aspharier.questlife.presentation.rewards.ComboIndicator
import com.aspharier.questlife.presentation.settings.SettingsViewModel

import com.aspharier.questlife.domain.model.Achievement
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
        val profileViewModel: ProfileViewModel = hiltViewModel()

        val questsViewModel: QuestsViewModel = hiltViewModel()
        val settingsViewModel: SettingsViewModel = hiltViewModel()

        val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()

        val questsState by questsViewModel.uiState.collectAsStateWithLifecycle()
        val hasSeenWelcome by settingsViewModel.hasSeenWelcome.collectAsStateWithLifecycle()



        var comboCount by remember { mutableIntStateOf(0) }
        var comboTimeRemaining by remember { mutableLongStateOf(0L) }
        var achievementsToShow by remember { mutableStateOf<List<Achievement>>(emptyList()) }

        val gameColors = LocalGameColors.current

        val companionType = remember(profileState.persona.avatarClass) {
                getCompanionForClass(profileState.persona.avatarClass.name)
        }



        Box(modifier = Modifier.fillMaxSize()) {
                // Theme background gradient
                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(
                                        Brush.verticalGradient(
                                                colors = gameColors.screenBackgroundGradient
                                        )
                                )
                )

                // Ambient particles
                GlowingBackground(
                        primaryColor = gameColors.glowPrimary,
                        secondaryColor = gameColors.glowSecondary
                )
                AmbientParticles(
                        colors = gameColors.particleColors,
                        particleCount = 25
                )

                val lazyListState = androidx.compose.foundation.lazy.rememberLazyListState()
                LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 96.dp),
                        flingBehavior = androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior()
                ) {
                        item {
                                Spacer(Modifier.height(8.dp))


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
                                        GameSectionHeader(title = "Daily Quests", textColor = Color.White)
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

                        }
                }

                // Level-up overlay
                if (profileState.levelUp) {
                        LevelUpAnimation(level = profileState.level)
                }

                // Achievement unlock animations
                if (achievementsToShow.isNotEmpty()) {
                        AchievementUnlockAnimation(
                                achievements = achievementsToShow,
                                onDismiss = { achievementsToShow = emptyList() }
                        )
                }

                // First-launch welcome popup
                if (!hasSeenWelcome) {
                        WelcomeDialog(
                                onDismiss = { settingsViewModel.setHasSeenWelcome(true) }
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
fun WelcomeDialog(onDismiss: () -> Unit) {
        val aiMessage = remember { AIMotivationSystem.getTimeBasedMessage() }

        val infiniteTransition = rememberInfiniteTransition(label = "welcomeGlow")
        val glowAlpha by infiniteTransition.animateFloat(
                initialValue = 0.3f, targetValue = 0.7f,
                animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                ), label = "glowAlpha"
        )

        androidx.compose.ui.window.Dialog(
                onDismissRequest = onDismiss,
                properties = androidx.compose.ui.window.DialogProperties(
                        usePlatformDefaultWidth = false
                )
        ) {
                Card(
                        modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .shadow(
                                        elevation = 24.dp,
                                        shape = RoundedCornerShape(28.dp),
                                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                                ),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                ) {
                        Column(
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                                Brush.verticalGradient(
                                                        colors = listOf(
                                                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                                                                Color.Transparent
                                                        )
                                                )
                                        )
                                        .padding(28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                // Welcome icon
                                Text(
                                        text = "⚔️",
                                        fontSize = 48.sp
                                )

                                Spacer(Modifier.height(16.dp))

                                // Title
                                Text(
                                        text = "Welcome, Adventurer!",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                        text = "Your journey begins now",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                )

                                Spacer(Modifier.height(20.dp))

                                // AI message card
                                Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = glowAlpha * 0.5f)
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                ) {
                                        Row(
                                                modifier = Modifier.padding(14.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                        ) {
                                                val moodEmoji = when (aiMessage.mood) {
                                                        com.aspharier.questlife.core.ai.AIMood.ENTHUSIASTIC -> "⚡"
                                                        com.aspharier.questlife.core.ai.AIMood.CALM -> "🌙"
                                                        com.aspharier.questlife.core.ai.AIMood.CHEERING -> "🎉"
                                                        com.aspharier.questlife.core.ai.AIMood.FOCUSED -> "🎯"
                                                        com.aspharier.questlife.core.ai.AIMood.MYSTERIOUS -> "✨"
                                                        com.aspharier.questlife.core.ai.AIMood.PLAYFUL -> "🤪"
                                                }

                                                Text(text = moodEmoji, fontSize = 28.sp)
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                                text = "AI Companion",
                                                                style = MaterialTheme.typography.labelSmall,
                                                                color = MaterialTheme.colorScheme.primary,
                                                                fontWeight = FontWeight.Bold
                                                        )
                                                        Spacer(Modifier.height(2.dp))
                                                        Text(
                                                                text = aiMessage.text,
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                                                        )
                                                        if (aiMessage.xpBonus > 0) {
                                                                Spacer(Modifier.height(4.dp))
                                                                Text(
                                                                        text = "+${aiMessage.xpBonus} XP bonus available!",
                                                                        style = MaterialTheme.typography.labelSmall,
                                                                        color = MaterialTheme.colorScheme.tertiary,
                                                                        fontWeight = FontWeight.Bold
                                                                )
                                                        }
                                                }
                                        }
                                }

                                Spacer(Modifier.height(28.dp))

                                // Dismiss button
                                Button(
                                        onClick = onDismiss,
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .height(52.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary
                                        ),
                                        elevation = ButtonDefaults.buttonElevation(
                                                defaultElevation = 8.dp
                                        )
                                ) {
                                        Text(
                                                text = "Let's Go! ⚔️",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 1.sp
                                        )
                                }
                        }
                }
        }
}
