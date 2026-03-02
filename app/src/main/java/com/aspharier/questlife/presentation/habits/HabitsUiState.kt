package com.aspharier.questlife.presentation.habits

import com.aspharier.questlife.domain.model.HabitWithStreak

data class HabitsUiState(
        val isLoading: Boolean = true,
        val habits: List<HabitWithStreak> = emptyList(),
        val error: String? = null
)
