package com.aspharier.questlife.presentation.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aspharier.questlife.domain.model.HabitDifficulty

@Composable
fun CreateHabitSheet(onCreate: (String, HabitDifficulty) -> Unit, onDismiss: () -> Unit) {

    var name by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf(HabitDifficulty.EASY) }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(text = "Create Habit", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Habit Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Text(text = "Difficulty", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(8.dp))

        HabitDifficulty.values().forEach { diff ->
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = diff.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge
                )
                RadioButton(selected = difficulty == diff, onClick = { difficulty = diff })
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onCreate(name.trim(), difficulty)
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth()
        ) { Text("Create Quest") }

        Spacer(Modifier.height(16.dp))
    }
}
