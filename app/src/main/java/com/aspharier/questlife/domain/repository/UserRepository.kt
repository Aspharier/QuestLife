package com.aspharier.questlife.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeTotalXp(): Flow<Int>
}