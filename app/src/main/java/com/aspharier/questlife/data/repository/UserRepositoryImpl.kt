package com.aspharier.questlife.data.repository

import com.aspharier.questlife.data.local.dao.CompletionDao
import com.aspharier.questlife.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val completionDao: CompletionDao
) : UserRepository {

    override fun observeTotalXp(): Flow<Int> {
        return completionDao.observeAllCompletions()
            .map { completions ->
                completions.sumOf { it.xpAwarded }
            }
    }
}