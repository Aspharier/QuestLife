package com.aspharier.questlife.presentation.habits

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.aspharier.questlife.core.ui.theme.QuestCard
import com.aspharier.questlife.domain.model.Habit

@Composable
fun HabitListItem(habit: Habit, onClick: () -> Unit, onLongPress: () -> Unit) {
    QuestCard(
            modifier =
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp).pointerInput(Unit) {
                        detectTapGestures(onTap = { onClick() }, onLongPress = { onLongPress() })
                    }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = habit.name, style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(4.dp))

            Text(
                    text =
                            "${habit.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }} · ${habit.category.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
