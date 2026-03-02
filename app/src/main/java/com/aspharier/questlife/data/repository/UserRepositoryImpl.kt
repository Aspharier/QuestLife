package com.aspharier.questlife.data.repository

import com.aspharier.questlife.data.local.dao.CompletionDao
import com.aspharier.questlife.data.local.entities.CompletionEntity
import com.aspharier.questlife.domain.repository.UserRepository
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(private val completionDao: CompletionDao) :
        UserRepository {

    override fun observeTotalXp(): Flow<Int> {
        return completionDao.observeAllCompletions().map { completions ->
            completions.sumOf { it.xpAwarded }
        }
    }

    /** Grants XP by inserting a synthetic completion record (with habitId = "system"). */
    override suspend fun addXp(amount: Int) {
        completionDao.insertCompletion(
                CompletionEntity(
                        id = UUID.randomUUID().toString(),
                        habitId = "system_quest_reward",
                        completedAt = System.currentTimeMillis(),
                        xpAwarded = amount,
                        currentStreak = 0,
                        wasFreezeUsed = false
                )
        )
    }
}
