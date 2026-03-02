package com.aspharier.questlife.presentation.quests

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.aspharier.questlife.domain.model.Quest

@Composable
fun QuestsScreen(viewModel: QuestsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Daily", "Weekly", "Achievements")

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
                text = "Quests",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                    text = title,
                                    fontWeight =
                                            if (selectedTab == index) FontWeight.Bold
                                            else FontWeight.Normal
                            )
                        }
                )
            }
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val quests =
                    when (selectedTab) {
                        0 -> uiState.dailyQuests
                        1 -> uiState.weeklyQuests
                        else -> uiState.achievements
                    }

            if (quests.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                            text = "No quests available here yet.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                ) { items(quests, key = { it.id }) { quest -> QuestCard(quest = quest) } }
            }
        }
    }
}

@Composable
fun QuestCard(quest: Quest) {
    val progress =
            if (quest.conditionTarget > 0)
                    (quest.progressCurrent.toFloat() / quest.conditionTarget).coerceIn(0f, 1f)
            else 1f

    val cardColor by
            animateColorAsState(
                    targetValue =
                            if (quest.isCompleted)
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            else MaterialTheme.colorScheme.surfaceVariant,
                    animationSpec = tween(400),
                    label = "cardColor"
            )

    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation =
                    CardDefaults.cardElevation(
                            defaultElevation = if (quest.isCompleted) 1.dp else 3.dp
                    )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                // Title + optional badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                            text = quest.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color =
                                    if (quest.isCompleted)
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f, fill = false)
                    )
                    quest.rewardBadge?.let { badge ->
                        Spacer(Modifier.width(6.dp))
                        Text(text = badge, fontSize = 16.sp)
                    }
                }

                // Completed or XP reward chip
                if (quest.isCompleted) {
                    Box(
                            modifier =
                                    Modifier.clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF22C55E).copy(alpha = 0.2f))
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                                text = "✓ Done",
                                fontSize = 12.sp,
                                color = Color(0xFF22C55E),
                                fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Box(
                            modifier =
                                    Modifier.clip(RoundedCornerShape(8.dp))
                                            .background(
                                                    MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.12f
                                                    )
                                            )
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                                text = "+${quest.rewardXp} XP",
                                fontSize = 12.sp,
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
                            text = "${quest.progressCurrent} / ${quest.conditionTarget}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(4.dp))
                LinearProgressIndicator(
                        progress = { progress },
                        modifier =
                                Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)),
                        color =
                                if (quest.isCompleted) Color(0xFF22C55E)
                                else MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            }
        }
    }
}
