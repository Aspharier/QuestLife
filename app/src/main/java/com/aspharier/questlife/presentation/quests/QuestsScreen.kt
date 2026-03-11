package com.aspharier.questlife.presentation.quests

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspharier.questlife.core.ui.animations.FadeInEntrance
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.core.ui.components.StatBar
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.domain.model.Quest

@Composable
fun QuestsScreen(viewModel: QuestsViewModel = hiltViewModel()) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        var selectedTab by remember { mutableIntStateOf(0) }
        val tabs = listOf("Daily" to "📋", "Weekly" to "📅", "Achievements" to "🏆")
        val gameColors = LocalGameColors.current

        Column(modifier = Modifier.fillMaxSize()) {
                GameSectionHeader(
                        title = "Quests",
                        icon = "📜",
                        modifier = Modifier.padding(top = 12.dp)
                )

                Spacer(Modifier.height(8.dp))

                // Game-style segmented tab bar
                Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                        tabs.forEachIndexed { index, (title, icon) ->
                                val selected = selectedTab == index
                                Box(
                                        modifier =
                                                Modifier.weight(1f)
                                                        .clip(RoundedCornerShape(12.dp))
                                                        .background(
                                                                if (selected)
                                                                        MaterialTheme.colorScheme
                                                                                .primary.copy(
                                                                                alpha = 0.15f
                                                                        )
                                                                else gameColors.panelBackground
                                                        )
                                                        .then(
                                                                if (selected) Modifier
                                                                else
                                                                        Modifier.background(
                                                                                Color.Transparent
                                                                        )
                                                        )
                                                        .clickable { selectedTab = index }
                                                        .padding(vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                ) {
                                        Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                        ) {
                                                Text(text = icon, fontSize = 14.sp)
                                                Spacer(Modifier.width(4.dp))
                                                Text(
                                                        text = title,
                                                        style = MaterialTheme.typography.labelLarge,
                                                        fontWeight =
                                                                if (selected) FontWeight.Bold
                                                                else FontWeight.Normal,
                                                        color =
                                                                if (selected)
                                                                        MaterialTheme.colorScheme
                                                                                .primary
                                                                else
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant
                                                )
                                        }
                                }
                        }
                }

                Spacer(Modifier.height(8.dp))

                if (uiState.isLoading) {
                        Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) }
                } else {
                        AnimatedContent(
                                targetState = selectedTab,
                                transitionSpec = {
                                        if (targetState > initialState) {
                                                        (slideInHorizontally { width -> width } +
                                                                        fadeIn())
                                                                .togetherWith(
                                                                        slideOutHorizontally { width
                                                                                ->
                                                                                -width
                                                                        } + fadeOut()
                                                                )
                                                } else {
                                                        (slideInHorizontally { width -> -width } +
                                                                        fadeIn())
                                                                .togetherWith(
                                                                        slideOutHorizontally { width
                                                                                ->
                                                                                width
                                                                        } + fadeOut()
                                                                )
                                                }
                                                .using(SizeTransform(clip = false))
                                },
                                label = "tabContent"
                        ) { targetTab ->
                                val quests =
                                        when (targetTab) {
                                                0 -> uiState.dailyQuests
                                                1 -> uiState.weeklyQuests
                                                else -> uiState.achievements
                                        }

                                if (quests.isEmpty()) {
                                        Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                        ) {
                                                Text(
                                                        text = "No quests available here yet.",
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color =
                                                                MaterialTheme.colorScheme
                                                                        .onSurfaceVariant
                                                )
                                        }
                                } else {
                                        LazyColumn(
                                                contentPadding =
                                                        PaddingValues(
                                                                vertical = 8.dp,
                                                                horizontal = 16.dp
                                                        ),
                                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.fillMaxSize()
                                        ) {
                                                itemsIndexed(
                                                        quests,
                                                        key = { _, quest -> quest.id }
                                                ) { index, quest ->
                                                        FadeInEntrance(index = index) {
                                                                QuestCard(quest = quest)
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
}

@Composable
fun QuestCard(quest: Quest) {
        val gameColors = LocalGameColors.current
        val progress =
                if (quest.conditionTarget > 0)
                        (quest.progressCurrent.toFloat() / quest.conditionTarget).coerceIn(0f, 1f)
                else 1f

        GamePanel(
                modifier = Modifier.fillMaxWidth().bounceClickable {},
                borderColor =
                        if (quest.isCompleted) gameColors.completedGreen.copy(alpha = 0.3f)
                        else gameColors.panelBorder,
                glowColor =
                        if (quest.isCompleted) gameColors.completedGreen.copy(alpha = 0.05f)
                        else gameColors.panelBorderGlow
        ) {
                Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                                // Title + badge
                                Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f, fill = false)
                                ) {
                                        Text(
                                                text = quest.title,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                color =
                                                        if (quest.isCompleted)
                                                                MaterialTheme.colorScheme.onSurface
                                                                        .copy(alpha = 0.6f)
                                                        else MaterialTheme.colorScheme.onSurface
                                        )
                                        quest.rewardBadge?.let { badge ->
                                                Spacer(Modifier.width(6.dp))
                                                Text(text = badge, fontSize = 14.sp)
                                        }
                                }

                                // Status chip
                                if (quest.isCompleted) {
                                        Box(
                                                modifier =
                                                        Modifier.clip(RoundedCornerShape(8.dp))
                                                                .background(
                                                                        gameColors.completedGreen
                                                                                .copy(alpha = 0.15f)
                                                                )
                                                                .padding(
                                                                        horizontal = 10.dp,
                                                                        vertical = 4.dp
                                                                )
                                        ) {
                                                Text(
                                                        text = "✓ Done",
                                                        fontSize = 11.sp,
                                                        color = gameColors.completedGreen,
                                                        fontWeight = FontWeight.Bold
                                                )
                                        }
                                } else {
                                        Box(
                                                modifier =
                                                        Modifier.clip(RoundedCornerShape(8.dp))
                                                                .background(
                                                                        MaterialTheme.colorScheme
                                                                                .primary.copy(
                                                                                alpha = 0.12f
                                                                        )
                                                                )
                                                                .padding(
                                                                        horizontal = 10.dp,
                                                                        vertical = 4.dp
                                                                )
                                        ) {
                                                Text(
                                                        text = "+${quest.rewardXp} XP",
                                                        fontSize = 11.sp,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        fontWeight = FontWeight.Bold
                                                )
                                        }
                                }
                        }

                        Spacer(Modifier.height(4.dp))

                        Text(
                                text = quest.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.height(10.dp))

                        // Progress bar
                        Column {
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                        Text(
                                                text = "Progress",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                                text =
                                                        "${quest.progressCurrent} / ${quest.conditionTarget}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                }
                                Spacer(Modifier.height(4.dp))
                                StatBar(
                                        progress = progress,
                                        barColor =
                                                if (quest.isCompleted) gameColors.completedGreen
                                                else MaterialTheme.colorScheme.primary,
                                        barColorEnd =
                                                if (quest.isCompleted) gameColors.completedGreen
                                                else MaterialTheme.colorScheme.tertiary,
                                        height = 8.dp
                                )
                        }
                }
        }
}
