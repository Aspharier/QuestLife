package com.aspharier.questlife.presentation.quests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.animations.bounceClickable
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.StatBar
import com.aspharier.questlife.core.ui.theme.LocalGameColors
import com.aspharier.questlife.domain.model.Quest

@Composable
fun QuestCard(quest: Quest) {
    val gameColors = LocalGameColors.current
    val progress = if (quest.conditionTarget > 0) {
        (quest.progressCurrent.toFloat() / quest.conditionTarget).coerceIn(0f, 1f)
    } else 1f

    GamePanel(
        modifier = Modifier.fillMaxWidth().bounceClickable {},
        borderColor = if (quest.isCompleted) gameColors.completedGreen.copy(alpha = 0.3f) else gameColors.panelBorder,
        glowColor = if (quest.isCompleted) gameColors.completedGreen.copy(alpha = 0.05f) else gameColors.panelBorderGlow
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = quest.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (quest.isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    quest.rewardBadge?.let { badge ->
                        Spacer(Modifier.padding(start = 6.dp))
                        Text(text = badge, fontSize = 14.sp)
                    }
                }

                if (quest.isCompleted) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .background(
                                gameColors.completedGreen.copy(alpha = 0.15f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
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
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
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
                StatBar(
                    progress = progress,
                    barColor = if (quest.isCompleted) gameColors.completedGreen else gameColors.glowPrimary,
                    barColorEnd = if (quest.isCompleted) gameColors.completedGreen else gameColors.glowSecondary,
                    height = 8.dp
                )
            }
        }
    }
}
