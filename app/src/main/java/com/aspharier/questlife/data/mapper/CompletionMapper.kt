package com.aspharier.questlife.data.mapper

import com.aspharier.questlife.data.local.entities.CompletionEntity
import com.aspharier.questlife.domain.model.Completion

object CompletionMapper {

    fun CompletionEntity.toDomain(): Completion {
        return Completion(
            id = id,
            habitId = habitId,
            completedAt = completedAt,
            xpAwarded = xpAwarded,
            currentStreak = currentStreak,
            wasFreezeUsed = wasFreezeUsed
        )
    }
}