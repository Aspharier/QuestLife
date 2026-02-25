package com.aspharier.questlife.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aspharier.questlife.data.local.dao.CompletionDao
import com.aspharier.questlife.data.local.dao.EquipmentDao
import com.aspharier.questlife.data.local.dao.HabitDao
import com.aspharier.questlife.data.local.entities.CompletionEntity
import com.aspharier.questlife.data.local.entities.EquipmentEntity
import com.aspharier.questlife.data.local.entities.HabitEntity

@Database(
        entities = [HabitEntity::class, CompletionEntity::class, EquipmentEntity::class],
        version = 2,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuestLifeDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun completionDao(): CompletionDao
    abstract fun equipmentDao(): EquipmentDao
}
