package com.aspharier.questlife.presentation.quests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aspharier.questlife.core.ui.theme.QuestCard

@Composable
fun QuestsScreen() {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Daily", "Weekly", "Achievements")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Quests",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        TabRow(
            selectedTabIndex = selectedTab
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuestCard(
                title = "Complete 5 Habits",
                reward = "+150 XP"
            )
            QuestCard(
                title = "Finish All Habits",
                reward = "+500 XP"
            )
        }
    }
}
