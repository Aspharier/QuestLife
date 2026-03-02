package com.aspharier.questlife.presentation.habits

import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.model.HabitCategory
import com.aspharier.questlife.domain.model.HabitDifficulty
import com.aspharier.questlife.domain.model.HabitFrequency

sealed interface HabitsEvent {

    data class CreateHabit(
            val name: String,
            val difficulty: HabitDifficulty,
            val category: HabitCategory,
            val frequency: HabitFrequency,
            val frequencyDays: List<String>,
            val description: String?
    ) : HabitsEvent

    data class DeactivateHabit(val habitId: String) : HabitsEvent

    data class CompleteHabit(val habit: Habit) : HabitsEvent
}
