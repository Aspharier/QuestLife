package com.aspharier.questlife.presentation.habits

import com.aspharier.questlife.domain.model.Habit

data class HabitsUiState(
    val isLoading: Boolean = true,
    val habits: List<Habit> = emptyList(),
    val error: String? = null
)
