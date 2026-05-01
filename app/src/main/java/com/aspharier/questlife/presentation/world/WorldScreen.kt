package com.aspharier.questlife.presentation.world

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspharier.questlife.core.ui.components.GamePanel
import com.aspharier.questlife.core.ui.components.GameSectionHeader
import com.aspharier.questlife.presentation.screens.GameScreenBackground

@Composable
fun WorldScreen() {
    GameScreenBackground {
        LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                GameSectionHeader(
                        title = "World",
                        modifier = Modifier.padding(top = 12.dp)
                )
            }

            item {
                GamePanel(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Column(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                                modifier =
                                        Modifier.size(168.dp)
                                                .background(
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                                                        RoundedCornerShape(20.dp)
                                                ),
                                contentAlignment = Alignment.Center
                        ) {
                            Text(text = "WORLD", style = MaterialTheme.typography.labelLarge)
                        }

                        Spacer(Modifier.height(18.dp))

                        Text(
                                text = "A living map shaped by your habits",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                                text = "Regions unlock as your routines become stronger.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                GamePanel(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                                text = "Current Region",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                                text = "The First Camp",
                                style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                                text = "Build daily momentum to expand the map.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
