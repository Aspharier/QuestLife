package com.aspharier.questlife.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aspharier.questlife.data.local.dao.CompletionDao
import com.aspharier.questlife.data.local.dao.HabitDao
import com.aspharier.questlife.data.local.entities.CompletionEntity
import com.aspharier.questlife.data.local.entities.HabitEntity

@Database(
    entities = [
        HabitEntity::class,
        CompletionEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class QuestLifeDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun completionDao(): CompletionDao
}