package com.aspharier.questlife.presentation.habits

import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.model.HabitDifficulty

sealed interface HabitsEvent {

    data class CreateHabit(
        val name: String,
        val difficulty: HabitDifficulty
    ) : HabitsEvent

    data class DeactivateHabit(
        val habitId: String
    ) : HabitsEvent
}