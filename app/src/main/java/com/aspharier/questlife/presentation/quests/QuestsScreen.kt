package com.aspharier.questlife.presentation.quests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun QuestsScreen() {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Daily", "Weekly", "Achievements")

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
                text = "Quests",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                )
            }
        }

        LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 0.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
        ) {
            item { QuestCard(title = "Complete 5 Habits", reward = "+150 XP") }
            item { QuestCard(title = "Finish All Daily Habits", reward = "+500 XP") }
            item { QuestCard(title = "7-Day Streak", reward = "+1000 XP") }
            item { QuestCard(title = "Log a habit 30 days in a row", reward = "+3000 XP + Badge") }
        }
    }
}
