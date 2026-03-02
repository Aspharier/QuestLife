package com.aspharier.questlife.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.theme.QuestCard

@Composable
fun StatsRow(hp: Int, atk: Int, def: Int, mana: Int, luck: Int) {
    QuestCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("❤️", "HP", hp.toString())
            StatItem("⚔️", "ATK", atk.toString())
            StatItem("🛡️", "DEF", def.toString())
            StatItem("✨", "MANA", mana.toString())
            StatItem("🍀", "LUCK", luck.toString())
        }
    }
}

@Composable
fun StatItem(icon: String, label: String, value: String) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(icon, fontSize = 22.sp)
        Spacer(Modifier.height(4.dp))
        Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
        )
        Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
