package com.aspharier.questlife.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun AvatarHeroSection(level: Int, totalXp: Int, progress: Float) {
    Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar circle with gradient background
        Box(
                modifier =
                        Modifier.size(110.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
        ) { Text("⚔️", fontSize = 52.sp) }

        Spacer(Modifier.height(12.dp))

        Text(
                text = "Warrior",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
        )

        Spacer(Modifier.height(4.dp))

        Text(
                text = "Level $level",
                style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(Modifier.height(12.dp))

        // XP Bar
        Box(
                modifier =
                        Modifier.fillMaxWidth(0.75f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                    modifier =
                            Modifier.fillMaxWidth(progress.coerceIn(0f, 1f))
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(xpGold)
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
                text = "$totalXp XP",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
