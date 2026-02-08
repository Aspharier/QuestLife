package com.aspharier.questlife.domain.model

data class Habit(
    val id: String,
    val name: String,
    val iconId: String,
    val colorHex: String,

    val frequency: HabitFrequency,
    val frequencyDays: List<String>,

    val difficulty: HabitDifficulty,
    val category: HabitCategory,

    val reminderTimes: List<String>,
    val description: String?,

    val createdAt: Long,
    val isActive: Boolean,
    val sortOrder: Int
)
