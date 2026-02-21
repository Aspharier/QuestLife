package com.aspharier.questlife.presentation.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aspharier.questlife.domain.model.UserStats

@Composable
fun AvatarStatsCard(
    stats: UserStats
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Hero Stats",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            StatRow("❤️ HP", stats.hp)
            StatRow("⚔ ATK", stats.atk)
            StatRow("🛡 DEF", stats.def)
            StatRow("✨ MANA", stats.mana)
            StatRow("🍀 LUCK", stats.luck)
        }
    }
}

@Composable
private fun StatRow(
    label: String,
    value: Int
) {

    val animatedValue by animateIntAsState(
        targetValue = value,
        animationSpec = tween(500),
        label = "statAnim"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(animatedValue.toString())
    }
}