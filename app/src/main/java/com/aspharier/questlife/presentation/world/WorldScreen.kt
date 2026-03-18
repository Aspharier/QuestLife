package com.aspharier.questlife.presentation.world

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aspharier.questlife.core.ui.theme.TextureOverlay
import com.questlife.app.R

@Composable
fun WorldScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Background texture
        TextureOverlay(
            texture = R.drawable.tex_stone_panel,
            alpha = if (isSystemInDarkTheme()) 0.08f else 0.05f,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your World",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "A living world shaped by your habits",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            // World preview placeholder

            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🌍",
                    fontSize = MaterialTheme.typography.displayLarge.fontSize
                )
            }
        }
    }
}
