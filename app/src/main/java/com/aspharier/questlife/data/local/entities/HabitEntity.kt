package com.aspharier.questlife.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey
    val id: String,

    val name: String,
    val iconId: String,
    val colorHex: String,

    val frequency: String, // Daily, Weekly, Custom
    val frequencyDays: String?, // Json array

    val difficulty: String, // Easy, Medium, Hard
    val category: String, // Health, Productivity...

    val reminderTimes: String?, // Json Arrays
    val description: String?,

    val createdAt: Long,
    val isActive: Boolean = true,
    val sortOrder: Int = 0
)
