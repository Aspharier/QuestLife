package com.aspharier.questlife.presentation.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspharier.questlife.core.ui.theme.xpGold
import com.aspharier.questlife.presentation.profile.ProfileViewModel

@Composable
fun AvatarScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
        val profileUiState by profileViewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
                item {
                        Text(
                                text = "Your Avatar",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                }

                // Avatar preview
                item {
                        Box(
                                modifier =
                                        Modifier.size(160.dp)
                                                .clip(CircleShape)
                                                .background(
                                                        MaterialTheme.colorScheme.primaryContainer
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                                AvatarRenderer(
                                        state = AvatarState(isLevelUp = profileUiState.levelUp),
                                        modifier = Modifier.fillMaxSize()
                                )
                        }
                }

                item {
                        Spacer(Modifier.height(12.dp))
                        Text(
                                text = "Warrior", // TODO class name based on state
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                                text = "Level ${profileUiState.level}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                }

                item {
                        Spacer(Modifier.height(24.dp))

                        Card(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                colors =
                                        CardDefaults.cardColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                shape = RoundedCornerShape(16.dp)
                        ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                                text = "Hero Stats",
                                                style = MaterialTheme.typography.titleLarge,
                                                modifier = Modifier.padding(bottom = 12.dp)
                                        )

                                        AvatarStatLine(
                                                "❤️ HP",
                                                "${profileUiState.stats.maxHp}",
                                                MaterialTheme.colorScheme.error
                                        )
                                        AvatarStatLine(
                                                "⚔️ ATK",
                                                "${profileUiState.stats.attack}",
                                                MaterialTheme.colorScheme.primary
                                        )
                                        AvatarStatLine(
                                                "🛡️ DEF",
                                                "${profileUiState.stats.defense}",
                                                MaterialTheme.colorScheme.tertiary
                                        )
                                        AvatarStatLine(
                                                "✨ MANA",
                                                "${profileUiState.stats.mana}",
                                                MaterialTheme.colorScheme.secondary
                                        )
                                        AvatarStatLine(
                                                "🍀 LUCK",
                                                "${profileUiState.stats.luck}",
                                                xpGold
                                        )
                                }
                        }
                }

                item {
                        Spacer(Modifier.height(16.dp))

                        Card(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                colors =
                                        CardDefaults.cardColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                shape = RoundedCornerShape(16.dp)
                        ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                                text = "Class Abilities",
                                                style = MaterialTheme.typography.titleLarge,
                                                modifier = Modifier.padding(bottom = 12.dp)
                                        )
                                        AbilityChip("Berserker Strike", "+20% ATK on streak")
                                        Spacer(Modifier.height(8.dp))
                                        AbilityChip("Iron Will", "-15% damage taken")
                                        Spacer(Modifier.height(8.dp))
                                        AbilityChip("Battle Cry", "+10 XP on completion")
                                }
                        }
                }
        }
}

@Composable
private fun AvatarStatLine(
        label: String,
        value: String,
        color: androidx.compose.ui.graphics.Color
) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
                Text(label, style = MaterialTheme.typography.bodyLarge)
                Text(
                        text = value,
                        style = MaterialTheme.typography.titleLarge,
                        color = color,
                        fontWeight = FontWeight.Bold
                )
        }
}

@Composable
private fun AbilityChip(name: String, description: String) {
        Row(
                modifier =
                        Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
                Column {
                        Text(
                                text = name,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                        )
                        Text(
                                text = description,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                }
                Text("✦", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
        }
}
