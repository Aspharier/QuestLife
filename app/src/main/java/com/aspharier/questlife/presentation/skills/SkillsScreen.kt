package com.aspharier.questlife.presentation.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Skill(
        val emoji: String,
        val name: String,
        val description: String,
        val unlocked: Boolean,
        val level: Int = 0
)

private val skills =
        listOf(
                Skill("⚔️", "Swordsmanship", "Boosts ATK on streaks", unlocked = true, level = 3),
                Skill("🛡️", "Iron Guard", "Reduces XP loss", unlocked = true, level = 2),
                Skill("🔮", "Arcane Mind", "Unlocks MANA abilities", unlocked = true, level = 1),
                Skill("🏹", "Swift Shot", "Earns bonus XP on hard habits", unlocked = false),
                Skill("💎", "Fortitude", "Longer streaks = more XP", unlocked = false),
                Skill("🌀", "Time Warp", "Complete habits early for +XP", unlocked = false),
                Skill("🍀", "Lucky Star", "Chance to double XP", unlocked = false),
                Skill("🔥", "Inferno", "Streak multiplier x2", unlocked = false),
                Skill("⚡", "Storm", "Speed-boost daily quests", unlocked = false),
        )

@Composable
fun SkillsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
                text = "Skills",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Text(
                text = "Complete habits to unlock and upgrade skills",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
        ) { items(skills) { skill -> SkillNode(skill = skill) } }
    }
}

@Composable
private fun SkillNode(skill: Skill) {
    val alpha = if (skill.unlocked) 1f else 0.4f

    Card(
            shape = RoundedCornerShape(16.dp),
            colors =
                    CardDefaults.cardColors(
                            containerColor =
                                    if (skill.unlocked) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant
                    ),
            modifier = Modifier.alpha(alpha)
    ) {
        Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                    modifier =
                            Modifier.size(52.dp)
                                    .clip(CircleShape)
                                    .background(
                                            if (skill.unlocked)
                                                    MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.15f
                                                    )
                                            else
                                                    MaterialTheme.colorScheme.outline.copy(
                                                            alpha = 0.2f
                                                    )
                                    ),
                    contentAlignment = Alignment.Center
            ) { Text(text = if (skill.unlocked) skill.emoji else "🔒", fontSize = 26.sp) }

            Spacer(Modifier.height(8.dp))

            Text(
                    text = skill.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 2
            )

            if (skill.unlocked && skill.level > 0) {
                Spacer(Modifier.height(4.dp))
                Text(
                        text = "Lv ${skill.level}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
