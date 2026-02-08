package com.aspharier.questlife.domain.model

data class Completion(
    val id: String,
    val habitId: String,
    val completedAt: Long,

    val xpAwarded: Int,
    val currentStreak: Int,
    val wasFreezeUsed: Boolean
)
