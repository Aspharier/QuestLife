package com.aspharier.questlife.data.mapper

import android.R
import com.aspharier.questlife.data.local.entities.HabitEntity
import com.aspharier.questlife.domain.model.Habit
import com.aspharier.questlife.domain.model.HabitCategory
import com.aspharier.questlife.domain.model.HabitDifficulty
import com.aspharier.questlife.domain.model.HabitFrequency

object HabitMapper {

    fun HabitEntity.toDomain(): Habit {
        return Habit(
            id = id,
            name = name,
            iconId = iconId,
            colorHex = colorHex,
            frequency = HabitFrequency.valueOf(frequency),
            frequencyDays = frequencyDays?.split(",") ?: emptyList(),
            difficulty = HabitDifficulty.valueOf(difficulty),
            category = HabitCategory.valueOf(category),
            reminderTimes = reminderTimes?.split(",") ?: emptyList(),
            description = description,
            createdAt = createdAt,
            isActive = isActive,
            sortOrder = sortOrder
        )
    }

    fun Habit.toEntity(): HabitEntity {
        return HabitEntity(
            id = id,
            name = name,
            iconId = iconId,
            colorHex = colorHex,
            frequency = frequency.name,
            frequencyDays = frequencyDays.joinToString(","),
            difficulty = difficulty.name,
            category = category.name,
            reminderTimes = reminderTimes.joinToString(","),
            description = description,
            createdAt = createdAt,
            isActive = isActive,
            sortOrder = sortOrder
        )
    }
}