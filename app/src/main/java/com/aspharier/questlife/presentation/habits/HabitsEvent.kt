package com.aspharier.questlife.presentation.habits

import com.aspharier.questlife.domain.model.Habit

sealed interface HabitsEvent {

    data class CreateHabit(
        val habit: Habit
    ) : HabitsEvent

    data class DeactivateHabit(
        val habitId: String
    ) : HabitsEvent
}