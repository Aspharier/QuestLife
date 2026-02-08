package com.aspharier.questlife.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completions")
data class CompletionEntity(
    @PrimaryKey
    val id: String,

    val habitId: String,
    val completedAt: Long,

    val xpAwarded: Int,
    val currentStreak: Int, val wasFreezeUsed: Boolean = false
)
