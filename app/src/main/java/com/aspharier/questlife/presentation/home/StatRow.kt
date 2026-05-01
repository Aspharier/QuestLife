package com.aspharier.questlife.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.StatBar
import com.aspharier.questlife.core.ui.theme.LocalGameColors

@Composable
fun StatsRow(hp: Int, atk: Int, def: Int, mana: Int, luck: Int) {
    val gameColors = LocalGameColors.current
    val maxStat = maxOf(hp, atk, def, mana, luck, 1).toFloat()

    GamePanel(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatBarItem(
                    icon = "❤️",
                    label = "HP",
                    value = hp,
                    progress = hp / maxStat,
                    barColor = gameColors.healthBar,
                    barColorEnd = gameColors.healthBar.copy(alpha = 0.7f)
            )
            StatBarItem(
                    icon = "⚔️",
                    label = "ATK",
                    value = atk,
                    progress = atk / maxStat,
                    barColor = MaterialTheme.colorScheme.primary,
                    barColorEnd = MaterialTheme.colorScheme.tertiary
            )
            StatBarItem(
                    icon = "🛡️",
                    label = "DEF",
                    value = def,
                    progress = def / maxStat,
                    barColor = Color(0xFF60A5FA),
                    barColorEnd = Color(0xFF3B82F6)
            )
            StatBarItem(
                    icon = "✨",
                    label = "MANA",
                    value = mana,
                    progress = mana / maxStat,
                    barColor = gameColors.manaBar,
                    barColorEnd = Color(0xFF8B5CF6)
            )
            StatBarItem(
                    icon = "🍀",
                    label = "LUCK",
                    value = luck,
                    progress = luck / maxStat,
                    barColor = gameColors.xpBar,
                    barColorEnd = gameColors.xpBarSecondary
            )
        }
    }
}

@Composable
private fun StatBarItem(
        icon: String,
        label: String,
        value: Int,
        progress: Float,
        barColor: Color,
        barColorEnd: Color
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(icon, fontSize = 16.sp)
        Spacer(Modifier.width(6.dp))
        Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(36.dp)
        )
        StatBar(
                progress = progress,
                barColor = barColor,
                barColorEnd = barColorEnd,
                modifier = Modifier.weight(1f),
                height = 8.dp
        )
        Spacer(Modifier.width(8.dp))
        Text(
                text = "$value",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
        )
    }
}
