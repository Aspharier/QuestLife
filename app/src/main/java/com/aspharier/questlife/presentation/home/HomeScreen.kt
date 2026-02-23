package com.aspharier.questlife.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aspharier.questlife.presentation.avatar.AvatarClass
import com.aspharier.questlife.presentation.avatar.AvatarState
import com.aspharier.questlife.presentation.profile.LevelUpAnimation
import com.aspharier.questlife.presentation.profile.ProfileViewModel

@Composable
fun HomeScreen() {

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()
    val avatarState =
            AvatarState(avatarClass = AvatarClass.WARRIOR, isLevelUp = profileState.levelUp)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            item {
                AvatarHeroSection(
                        level = profileState.level,
                        totalXp = profileState.totalXp,
                        progress = profileState.progressToNextLevel
                )
            }

            item {
                Spacer(Modifier.height(8.dp))
                StatsRow()
            }

            item {
                Spacer(Modifier.height(24.dp))
                Text(
                        text = "Today's Habits",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(3) {
                Spacer(Modifier.height(12.dp))
                HabitPreviewCard(title = "Drink Water", meta = "Medium · Health · 🔥 12 days")
            }
        }

        // Level-up overlay — covers whole screen on top
        if (profileState.levelUp) {
            LevelUpAnimation(
                    level = profileState.level,
            )
        }

        // FAB
        FloatingActionButton(
                onClick = {},
                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
                containerColor = MaterialTheme.colorScheme.primary
        ) { Text("+", fontSize = 24.sp) }
    }
}
