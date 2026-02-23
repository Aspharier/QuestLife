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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.theme.xpGold

@Composable
fun AvatarScreen() {
    LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                    text = "Your Avatar",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        // Avatar preview
        item {
            Box(
                    modifier =
                            Modifier.size(160.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
            ) { Text("⚔️", fontSize = 72.sp) }
        }

        item {
            Spacer(Modifier.height(12.dp))
            Text(
                    text = "Warrior",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                    text = "Level 24",
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
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                    shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                            text = "Hero Stats",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 12.dp)
                    )

                    AvatarStatLine("❤️ HP", "340", MaterialTheme.colorScheme.error)
                    AvatarStatLine("⚔️ ATK", "45", MaterialTheme.colorScheme.primary)
                    AvatarStatLine("🛡️ DEF", "38", MaterialTheme.colorScheme.tertiary)
                    AvatarStatLine("✨ MANA", "52", MaterialTheme.colorScheme.secondary)
                    AvatarStatLine("🍀 LUCK", "29", xpGold)
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))

            Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors =
                            CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
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
