package com.aspharier.questlife.presentation.avatar

import com.aspharier.questlife.domain.model.Equipment

data class AvatarState(
        val avatarClass: AvatarClass = AvatarClass.WARRIOR,
        val equippedItems: List<Equipment> = emptyList(),
        val isLevelUp: Boolean = false
)
