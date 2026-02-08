package com.aspharier.questlife.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aspharier.questlife.data.local.entities.CompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionDao {

    @Query("""
        SELECT * FROM completions
        WHERE habitId = :habitId
        ORDER BY completedAt DESC
    """)
    fun observeCompletionsFroHabit(habitId: String): Flow<List<CompletionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: CompletionEntity)

    @Query("""
        SELECT * FROM completions
        WHERE completedAt BETWEEN :start AND :end
    """)
    suspend fun getCompletionsBetween(
        start: Long,
        end: Long
    ): List<CompletionEntity>
}